package top.reviewx.rest.user.review.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentReq {
    @NotBlank
    private String content;
}
