package top.reviewx.rest.user.review;

import top.reviewx.rest.basic.review.dto.res.ReviewRes;
import top.reviewx.rest.user.review.dto.req.CreateCommentReq;
import top.reviewx.rest.user.review.dto.req.CreateReactionReq;
import top.reviewx.rest.user.review.dto.req.CreateReviewReq;
import top.reviewx.rest.user.review.dto.req.UpdateCommentReq;

public interface UReviewService {

    ReviewRes createOrUpdateReview(CreateReviewReq req);

    String deleteReview(String objectId);

    ReviewRes createOrUpdateReaction(String id, CreateReactionReq req);

    ReviewRes createComment(String id, CreateCommentReq req);

    ReviewRes updateComment(String id, UpdateCommentReq req);

    String deleteComment(String id);
}
