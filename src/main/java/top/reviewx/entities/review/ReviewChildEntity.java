package top.reviewx.entities.review;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewChildEntity extends BaseEntity {
    private String content;
    private BaseCreatedBy createdBy;
    private Boolean isDelete;
    private List<ReactionEntity> reactions;
    private List<ReviewChildEntity> childs;
}
