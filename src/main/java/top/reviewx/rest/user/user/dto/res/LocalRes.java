package top.reviewx.rest.user.user.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalRes {
    private String email;
    private String name;
    private String avatar;
    private Boolean isActive;
}
