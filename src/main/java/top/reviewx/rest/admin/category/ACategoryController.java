package top.reviewx.rest.admin.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.rest.admin.category.dto.req.CategoryCreateReq;
import top.reviewx.rest.admin.category.dto.req.UpdateCategoryReq;

@Validated
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class ACategoryController {
    private final ACategoryService aCategoryService;

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody @Valid CategoryCreateReq req,
                                                 @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aCategoryService.createCategory(req));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CommonResponse> updateCategory(@PathVariable @NotBlank @Size(min = 36) String categoryId,
                                                         @RequestBody @Valid UpdateCategoryReq req,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aCategoryService.updateCategory(categoryId, req));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponse> deleteCategory(@PathVariable @NotBlank @Size(min = 36) String categoryId,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aCategoryService.deleteCategory(categoryId));
    }
}
