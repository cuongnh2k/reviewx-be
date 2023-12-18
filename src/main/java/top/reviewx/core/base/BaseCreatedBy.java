package top.reviewx.core.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseCreatedBy {
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String avatar;
}
