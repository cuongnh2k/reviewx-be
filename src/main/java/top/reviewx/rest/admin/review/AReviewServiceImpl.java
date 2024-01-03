package top.reviewx.rest.admin.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.entities.review.ReviewEntity;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AReviewServiceImpl implements AReviewService {
    private final AReviewRepository aReviewRepository;

    @Override
    public void deleteReview(String reviewId) {
        ReviewEntity reviewEntity = aReviewRepository.findByIdAndIsDeleteFalse(reviewId);
        if (reviewEntity != null) {
            reviewEntity.setIsDelete(true);
            reviewEntity.setUpdatedAt(LocalDateTime.now());
            aReviewRepository.save(reviewEntity);
        }
    }
}