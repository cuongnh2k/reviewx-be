package top.reviewx.rest.basic.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.review.ReviewEntity;

import java.util.List;

@Repository
public interface BReviewRepository extends MongoRepository<ReviewEntity, String> {
    Page<ReviewEntity> findByObjectIdAndRateAndIsDeleteFalse(String object, Float rate, Pageable pageable);

    Page<ReviewEntity> findByObjectIdAndIsDeleteFalse(String object, Pageable pageable);

    List<ReviewEntity> findByObjectIdAndIsDeleteFalse(String object);

    ReviewEntity findByIdAndIsDeleteFalse(String id);
}
