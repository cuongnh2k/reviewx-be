package top.reviewx.rest.admin.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.review.ReviewEntity;

@Repository
public interface AReviewRepository extends MongoRepository<ReviewEntity, String> {

    ReviewEntity findByObjectIdAndCreatedBy_IdAndIsDeleteFalse(String objectId, String createById);

    ReviewEntity findByIdAndIsDeleteFalse(String id);
}
