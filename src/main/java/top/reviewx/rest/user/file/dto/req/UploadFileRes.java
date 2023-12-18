package top.reviewx.rest.user.file.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadFileRes {
    private String id;
    private String name;
    private Long size;
    private String url;
}
