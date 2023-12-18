package top.reviewx.rest.admin.objectv1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.utils.PageableUtil;
import top.reviewx.rest.admin.objectv1.dto.req.UpdateObjectV1AdminReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/objects-v1")
public class AObjectV1Controller {
    private final AObjectV1Service aObjectV1Service;

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateObjectV1(@PathVariable @NotBlank @Size(min = 36, max = 36) String id,
                                                         @RequestBody @Valid UpdateObjectV1AdminReq req,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aObjectV1Service.updateObjectV1Admin(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getOneObjectV1Admin(@PathVariable String id,
                                                              @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aObjectV1Service.getOneObjectV1Admin(id));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getListObjectV1Admin(@RequestParam(defaultValue = "") String categoryId,
                                                               @RequestParam(defaultValue = "") String objectId,
                                                               @RequestParam(defaultValue = "") String name,
                                                               @RequestParam(defaultValue = "0") Integer pageNumber,
                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                               @RequestParam(defaultValue = "") String sort,
                                                               @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aObjectV1Service.getListObjectV1Admin(categoryId,
                objectId,
                name,
                new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }
}
