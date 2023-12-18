package top.reviewx.rest.basic.review;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.core.utils.PageableUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basic/reviews")
public class BReviewController {
    private final BReviewService bReviewService;

    @GetMapping
    public ResponseEntity<CommonResponse> getListReview(@RequestParam String objectId,
                                                        @RequestParam(required = false) Integer rate,
                                                        @RequestParam(defaultValue = "0") Integer pageNumber,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "") String sort,
                                                        @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bReviewService.getListReview(objectId,
                rate,
                new PageableUtil().buildPageable(pageNumber, pageSize, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getOneReview(@PathVariable String id,
                                                       @RequestHeader(name = "api-key") @NotBlank String apiKey) {
        return CommonResponse.success(bReviewService.getOneReview(id));
    }
}
