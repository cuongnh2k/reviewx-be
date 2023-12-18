package top.reviewx.rest.user.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.utils.PageableUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/notifications")
public class UNotificationController {

    private final UNotificationService uNotificationService;

    @GetMapping
    public ResponseEntity<CommonResponse> getList(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "") String sort,
                                                  @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(uNotificationService.getListNotification(new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> read(@PathVariable String id,
                                               @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        uNotificationService.read(id);
        return CommonResponse.success("success");
    }
}
