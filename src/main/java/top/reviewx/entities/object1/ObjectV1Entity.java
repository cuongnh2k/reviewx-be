package top.reviewx.entities.object1;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.enums.ObjectV1StatusEnum;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ObjectV1")
@EqualsAndHashCode(callSuper = true)
public class ObjectV1Entity extends BaseEntity {
    private String objectId;
    private String categoryId;
    private String name;
    private String avatar;
    private String address;
    private Boolean isDelete;
    private ObjectV1StatusEnum status;
    private List<NoteEntity> notes;
    private BaseCreatedBy createdBy;
}
