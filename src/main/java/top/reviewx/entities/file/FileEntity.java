package top.reviewx.entities.file;

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
@Document(collection = "File")
@EqualsAndHashCode(callSuper = true)
public class FileEntity extends BaseEntity {
    private String url;
    private String name;
    private Long size;
    private String contentType;
    private BaseCreatedBy createdBy;
}
