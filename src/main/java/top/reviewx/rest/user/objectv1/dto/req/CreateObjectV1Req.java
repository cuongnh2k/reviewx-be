package top.reviewx.rest.user.objectv1.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateObjectV1Req {
    private String objectId;
    @NotBlank
    @Size(min = 36)
    private String categoryId;
    @NotBlank
    private String name;
    private String avatar;
    @NotBlank
    private String address;
    private String note;
}
