package top.reviewx.rest.user.review.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReactionReq {
    @NotNull
    private Boolean isLike;
}
