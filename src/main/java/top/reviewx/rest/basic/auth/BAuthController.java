package top.reviewx.rest.basic.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.rest.basic.auth.dto.req.ActiveUserReq;
import top.reviewx.rest.basic.auth.dto.req.ResetPasswordReq;
import top.reviewx.rest.basic.auth.dto.req.SignInReq;
import top.reviewx.rest.basic.auth.dto.req.SignUpReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/basic/auths")
public class BAuthController {
    private final BUserService bUserService;

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse> signUp(@RequestBody @Valid SignUpReq req,
                                                 @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bUserService.signUp(req));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<CommonResponse> signIn(@RequestBody @Valid SignInReq req,
                                                 @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bUserService.signIn(req));
    }

    @PostMapping("/active")
    public ResponseEntity<CommonResponse> active(@RequestBody @Valid ActiveUserReq req,
                                                 @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bUserService.active(req));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody @Valid ResetPasswordReq req,
                                                        @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bUserService.resetPassword(req));
    }
}
