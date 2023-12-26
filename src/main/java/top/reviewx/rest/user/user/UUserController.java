package top.reviewx.rest.user.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.rest.user.user.dto.req.UpdateUserReq;

@Validated
@RestController
@RequestMapping("/user/users")
@RequiredArgsConstructor
public class UUserController {
    private final UUserService uUserService;

    @GetMapping
    public ResponseEntity<CommonResponse> getUser(@RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uUserService.getUser());
    }

    @PatchMapping
    public ResponseEntity<CommonResponse> updateUser(@RequestBody @Valid UpdateUserReq req,
                                                     @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uUserService.updateUser(req));
    }
}
