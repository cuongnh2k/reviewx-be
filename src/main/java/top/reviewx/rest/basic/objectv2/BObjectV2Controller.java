package top.reviewx.rest.basic.objectv2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.utils.PageableUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/basic/objects-v2")
public class BObjectV2Controller {
    private final BObjectV2Service bObjectV2Service;

    @GetMapping
    public ResponseEntity<CommonResponse> getListObjectV2(@RequestParam String categoryId,
                                                          @RequestParam(defaultValue = "") String name,
                                                          @RequestParam(defaultValue = "0") Integer pageNumber,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(defaultValue = "") String sort,
                                                          @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bObjectV2Service.getListObjectV2(categoryId,
                name,
                new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getOneObjectV2(@PathVariable @NotBlank @Size(min = 36, max = 36) String id,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bObjectV2Service.getOneObjectV2(id));
    }
}
