package top.reviewx.entities.category;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Category")
@EqualsAndHashCode(callSuper = true)
public class CategoryEntity extends BaseEntity {
    private String name;
    private Boolean isDelete;
    private List<CategoryChildEntity> childs;
    private BaseCreatedBy createdBy;
    private BaseCreatedBy updatedBy;
}
