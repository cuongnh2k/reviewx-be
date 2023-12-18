package top.reviewx.entities.object2;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ObjectV2")
@EqualsAndHashCode(callSuper = true)
public class ObjectV2Entity extends BaseEntity {
    private String categoryId;
    private String name;
    private String avatar;
    private String address;
    private BaseCreatedBy createdBy;
    private Boolean isDelete;
}
