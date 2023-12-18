package top.reviewx.rest.user.user.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.base.BaseResponse;
import top.reviewx.core.enums.RoleEnum;
import top.reviewx.entities.user.LocalEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRes extends BaseResponse {
    private List<RoleEnum> roles;
    private LocalRes local;
    private Object facebook;
    private Object google;
}
