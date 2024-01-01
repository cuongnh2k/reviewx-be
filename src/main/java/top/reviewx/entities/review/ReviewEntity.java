package top.reviewx.entities.review;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Review")
@EqualsAndHashCode(callSuper = true)
public class ReviewEntity extends BaseEntity {
    private String objectId;
    private float rate;
    private String content;
    private List<ReactionEntity> reactions;
    private BaseCreatedBy createdBy;
    private Boolean isDelete;
    private List<ReviewChildEntity> childs;
}
