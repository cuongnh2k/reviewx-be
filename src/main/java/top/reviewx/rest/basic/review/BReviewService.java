package top.reviewx.rest.basic.review;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.rest.basic.review.dto.res.ReviewRes;

public interface BReviewService {
    CommonListResponse<ReviewRes> getListReview(String objectId, Float rate, Pageable pageable);

    ReviewRes getOneReview(String id);
}
