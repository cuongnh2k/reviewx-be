package top.reviewx.rest.basic.review.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewChildRes extends BaseResponse {
    private String content;
    private BaseCreatedBy createdBy;
    private List<ReactionRes> reactions;
    private List<ReviewChildRes> childs;
}
