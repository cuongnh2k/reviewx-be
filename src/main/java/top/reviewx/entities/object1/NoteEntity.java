package top.reviewx.entities.object1;

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
public class NoteEntity extends BaseCreatedBy {
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
