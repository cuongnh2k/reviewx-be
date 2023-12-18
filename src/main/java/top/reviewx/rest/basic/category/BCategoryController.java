package top.reviewx.rest.basic.category;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reviewx.core.common.CommonResponse;

@Validated
@RestController
@RequestMapping("/basic/categories")
@RequiredArgsConstructor
public class BCategoryController {
    private final BCategoryService bCategoryService;

    @GetMapping
    public ResponseEntity<CommonResponse> getListCategory(@RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bCategoryService.getListCategory());
    }
}
