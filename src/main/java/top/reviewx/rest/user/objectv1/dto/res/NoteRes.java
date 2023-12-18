package top.reviewx.rest.user.objectv1.dto.res;

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
public class NoteRes extends BaseCreatedBy {
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
