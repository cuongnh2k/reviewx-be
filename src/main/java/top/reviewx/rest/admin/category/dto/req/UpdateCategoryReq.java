package top.reviewx.rest.admin.category.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryReq {
    @NotBlank
    private String name;
}
