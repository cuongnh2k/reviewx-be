package top.reviewx.rest.basic.auth.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUserReq {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\d{4}$")
    private String verifyToken;
}
