package top.reviewx.rest.basic.objectv2.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseResponse;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObjectV2Res extends BaseResponse {
    private String categoryId;
    private String name;
    private String avatar;
    private String address;
    private BaseCreatedBy createdBy;
    private Float averageRating;
}
