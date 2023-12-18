package top.reviewx.core.base;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
