package top.reviewx.rest.basic.auth.dto.res;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRes {
    private String token;
}
