package top.reviewx.entities.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalEntity {
    private String email;
    private String password;
    private String name;
    private String avatar;
    private Boolean isActive;
    private String verifyToken;
}
