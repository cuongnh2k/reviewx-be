package top.reviewx.entities.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.enums.RoleEnum;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "User")
public class UserEntity extends BaseEntity {
    private List<RoleEnum> roles;
    private LocalEntity local;
    private Object facebook;
    private Object google;
}
