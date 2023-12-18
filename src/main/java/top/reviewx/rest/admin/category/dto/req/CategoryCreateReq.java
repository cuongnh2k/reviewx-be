package top.reviewx.rest.admin.category.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateReq {
    @NotBlank
    private String name;
    private List<CategoryCreateReq> childs;
}
