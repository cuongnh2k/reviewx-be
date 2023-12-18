package top.reviewx.rest.admin.user;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.utils.PageableUtil;

@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AUserController {
    private final AUserService aUserService;

    @GetMapping
    public ResponseEntity<CommonResponse> getUser(@RequestParam(defaultValue = "") String name,
                                                  @RequestParam Boolean isActive,
                                                  @RequestParam(defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "") String sort,
                                                  @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(aUserService.getListUser(name,
                isActive,
                new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }
}
