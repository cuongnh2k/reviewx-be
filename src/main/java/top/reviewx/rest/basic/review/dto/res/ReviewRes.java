package top.reviewx.rest.basic.review.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewRes extends BaseResponse {
    private String objectId;
    private Integer rate;
    private String content;
    private List<ReactionRes> reactions;
    private BaseCreatedBy createdBy;
    private List<ReviewChildRes> childs;
}
