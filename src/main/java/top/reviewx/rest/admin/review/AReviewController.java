package top.reviewx.rest.admin.review;


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
@RequestMapping("/admin/reviews")
public class AReviewController {

    private final AReviewService aReviewService;

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponse> deleteReview(@PathVariable @NotBlank @Size(min = 36, max = 36) String reviewId,
                                                       @RequestHeader(name = "api-key") String apiKey) {
        aReviewService.deleteReview(reviewId);
        return CommonResponse.success("");
    }
}
