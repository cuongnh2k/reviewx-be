package top.reviewx.rest.basic.category.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryChildRes extends BaseResponse {
    private String name;
    private List<CategoryChildRes> childs;
    private BaseCreatedBy createdBy;
    private BaseCreatedBy updatedBy;
}
