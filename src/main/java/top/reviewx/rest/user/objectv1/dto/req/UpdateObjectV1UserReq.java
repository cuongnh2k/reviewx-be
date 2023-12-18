package top.reviewx.rest.user.objectv1.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.enums.ObjectV1StatusEnum;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectV1UserReq {
    private String name;
    private String avatar;
    private String address;
    private ObjectV1StatusEnum status;
    private String note;
}
