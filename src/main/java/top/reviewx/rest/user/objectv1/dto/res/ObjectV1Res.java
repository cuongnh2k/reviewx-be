package top.reviewx.rest.user.objectv1.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.base.BaseResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObjectV1Res extends BaseResponse {
    private String objectId;
    private String categoryId;
    private String name;
    private String avatar;
    private String address;
    private ObjectV1StatusEnum status;
    private List<NoteRes> notes;
    private BaseCreatedBy createdBy;
}
