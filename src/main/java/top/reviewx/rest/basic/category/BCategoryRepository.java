package top.reviewx.rest.basic.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.category.CategoryEntity;

import java.util.List;

@Repository
public interface BCategoryRepository extends MongoRepository<CategoryEntity, String> {
    List<CategoryEntity> findByIsDeleteFalse();

    CategoryEntity findByIdAndIsDeleteFalse(String id);
}
