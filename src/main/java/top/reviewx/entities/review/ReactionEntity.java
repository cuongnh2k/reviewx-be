package top.reviewx.entities.review;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReactionEntity extends BaseCreatedBy {
    private Boolean isLike;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
