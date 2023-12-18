package top.reviewx.core.common;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@Builder
@RequestScope
@NoArgsConstructor
@AllArgsConstructor
public class CommonAuthContext {
    private String id;
    private String name;
    private String avatar;

    public void set(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
