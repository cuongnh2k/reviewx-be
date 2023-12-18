package top.reviewx.entities.category;

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
public class CategoryChildEntity extends BaseEntity {
    private String name;
    private Boolean isDelete;
    private List<CategoryChildEntity> childs;
    private BaseCreatedBy createdBy;
    private BaseCreatedBy updatedBy;
}
