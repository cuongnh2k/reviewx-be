package top.reviewx.rest.user.file;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.reviewx.core.common.CommonResponse;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/files")
public class UFileController {

    private final UFileService uFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> uploadFile(@RequestPart MultipartFile file,
                                                     @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uFileService.uploadFile(file));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<CommonResponse> deleteFile(@PathVariable @Size(min = 36, max = 36) String fileId,
                                                     @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uFileService.deleteFile(fileId));
    }
}
