package top.reviewx.rest.user.review.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewReq {
    @NotBlank
    @Size(min = 36, max = 36)
    private String objectId;
    @Max(5)
    @Min(1)
    private Integer rate;
    @NotBlank
    private String content;
}
