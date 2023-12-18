package top.reviewx.rest.admin.objectv2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/objects-v2")
public class AObjectV2Controller {
    private final AObjectV2Service aObjectV2Service;

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteObjectV2(@PathVariable @NotBlank @Size(min = 36, max = 36) String id,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aObjectV2Service.deleteObjectV2(id));
    }
}
