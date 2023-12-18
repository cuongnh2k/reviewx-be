package top.reviewx.rest.user.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.exception.AccessDeniedException;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.file.FileEntity;
import top.reviewx.rest.user.file.dto.req.UploadFileRes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UFileServiceImpl implements UFileService {
    private final UFileRepository fileRepository;
    private final CommonAuthContext authContext;
    private final AmazonS3 amazonS3;
    @Value("${application.bucket.public}")
    private String BUCKET_PUBLIC;
    @Value("${cloud.aws.region.static}")
    private String REGION;
    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    public UploadFileRes uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        if (fileExtension.isEmpty()) {
            throw new BusinessLogicException();
        }
        String id = UUID.randomUUID().toString();
        FileEntity fileEntity = FileEntity.builder()
                .id(id)
                .url("https://s3." + REGION + ".amazonaws.com/" + BUCKET_PUBLIC + "/" + authContext.getId() + "/" + id + "." + fileExtension.toLowerCase())
                .name(originalFilename.toLowerCase())
                .size(file.getSize())
                .contentType(file.getContentType())
                .createdBy(BaseCreatedBy.builder()
                        .id(authContext.getId())
                        .name(authContext.getName())
                        .avatar(authContext.getAvatar())
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        fileRepository.insert(fileEntity);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3.putObject(BUCKET_PUBLIC,
                    authContext.getId() + "/" + fileEntity.getId() + "." + fileExtension.toLowerCase(),
                    file.getInputStream(),
                    metadata);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new BusinessLogicException();
        }
        return UploadFileRes.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .size(fileEntity.getSize())
                .url(fileEntity.getUrl())
                .build();
    }

    @Override
    public String deleteFile(String fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId).orElse(null);
        if (fileEntity == null) {
            throw new BusinessLogicException();
        }
        if (!fileEntity.getCreatedBy().getId().equals(authContext.getId())) {
            throw new AccessDeniedException();
        }
        fileRepository.deleteById(fileId);
        amazonS3.deleteObject(BUCKET_PUBLIC,
                fileEntity.getCreatedBy().getId() + "/" + fileEntity.getId() + fileEntity.getName().substring(fileEntity.getName().lastIndexOf(".")));
        return fileId;
    }
}