package top.reviewx.rest.user.review;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.reviewx.core.common.CommonResponse;
import top.reviewx.rest.user.review.dto.req.CreateCommentReq;
import top.reviewx.rest.user.review.dto.req.CreateReactionReq;
import top.reviewx.rest.user.review.dto.req.CreateReviewReq;
import top.reviewx.rest.user.review.dto.req.UpdateCommentReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reviews")
public class UReviewController {

    private final UReviewService uReviewService;

    @PostMapping
    public ResponseEntity<CommonResponse> createOrUpdateReview(@RequestBody @Valid CreateReviewReq req,
                                                               @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.createOrUpdateReview(req));
    }

    @DeleteMapping("/{objectId}")
    public ResponseEntity<CommonResponse> deleteReview(@PathVariable @NotBlank @Size(min = 36, max = 36) String objectId,
                                                       @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.deleteReview(objectId));
    }

    @PostMapping("/{reviewId}/reaction")
    public ResponseEntity<CommonResponse> createOrUpdateReaction(@PathVariable(name = "reviewId") @NotBlank @Size(min = 36) String id,
                                                                 @RequestBody @Valid CreateReactionReq req,
                                                                 @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.createOrUpdateReaction(id, req));
    }

    @PostMapping("/{reviewId}/comment")
    public ResponseEntity<CommonResponse> createComment(@PathVariable(name = "reviewId") @NotBlank @Size(min = 36) String id,
                                                        @RequestBody @Valid CreateCommentReq req,
                                                        @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.createComment(id, req));
    }

    @PatchMapping("/{reviewId}/comment")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable(name = "reviewId") @NotBlank @Size(min = 36) String id,
                                                        @RequestBody @Valid UpdateCommentReq req,
                                                        @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.updateComment(id, req));
    }

    @DeleteMapping("/{reviewId}/comment")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable(name = "reviewId") @NotBlank @Size(min = 36) String id,
                                                        @RequestHeader(name = "api-key") String apiKey) {
        return CommonResponse.success(uReviewService.deleteComment(id));
    }
}
