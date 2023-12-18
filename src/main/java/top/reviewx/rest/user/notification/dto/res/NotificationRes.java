package top.reviewx.rest.user.notification.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationRes extends BaseResponse {
    private List<String> receiver;
    private String objectV1Id;
    private String reviewId;
    private String content;
    private Boolean isRead;
}
