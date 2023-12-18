package top.reviewx.rest.admin.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.category.CategoryEntity;

@Repository
public interface ACategoryRepository extends MongoRepository<CategoryEntity, String> {
    CategoryEntity findByIdAndIsDeleteFalse(String id);
}
