package top.reviewx.rest.user.objectv1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.core.utils.PageableUtil;
import top.reviewx.rest.user.objectv1.dto.req.CreateObjectV1Req;
import top.reviewx.rest.user.objectv1.dto.req.UpdateObjectV1UserReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/objects-v1")
public class UObjectV1Controller {
    private final UObjectV1Service uObjectV1Service;

    @PostMapping
    public ResponseEntity<CommonResponse> createObjectV1(@RequestBody @Valid CreateObjectV1Req req,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uObjectV1Service.createObjectV1(req));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getListObjectV1User(@RequestParam(defaultValue = "") String categoryId,
                                                              @RequestParam(defaultValue = "") String objectId,
                                                              @RequestParam(defaultValue = "") String name,
                                                              @RequestParam ObjectV1StatusEnum status,
                                                              @RequestParam(defaultValue = "0") Integer pageNumber,
                                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestParam(defaultValue = "") String sort,
                                                              @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uObjectV1Service.getListObjectV1User(categoryId,
                objectId,
                name,
                status,
                new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getOneObjectV1User(@PathVariable String id,
                                                             @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uObjectV1Service.getOneObjectV1User(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateObjectV1(@PathVariable @NotBlank @Size(min = 36, max = 36) String id,
                                                         @RequestBody @Valid UpdateObjectV1UserReq req,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uObjectV1Service.updateObjectV1User(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteObjectV1(@PathVariable @NotBlank @Size(min = 36, max = 36) String id,
                                                         @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        uObjectV1Service.deleteObjectV1User(id);
        return CommonResponse.success("");
    }
}
