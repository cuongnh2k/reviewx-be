package top.reviewx.rest.user.file;

import org.springframework.web.multipart.MultipartFile;
import top.reviewx.rest.user.file.dto.req.UploadFileRes;

public interface UFileService {

    UploadFileRes uploadFile(MultipartFile file);

    String deleteFile(String fileId);
}
