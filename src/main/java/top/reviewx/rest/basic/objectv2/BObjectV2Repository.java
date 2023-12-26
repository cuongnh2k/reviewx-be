package top.reviewx.rest.basic.objectv2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.object2.ObjectV2Entity;

@Repository
public interface BObjectV2Repository extends MongoRepository<ObjectV2Entity, String> {

    Page<ObjectV2Entity> findByCategoryIdAndNameContainingAndIsDeleteFalse(String categoryId, String name, Pageable pageable);

    Page<ObjectV2Entity> findByCategoryIdAndIsDeleteFalse(String categoryId, Pageable pageable);

    Page<ObjectV2Entity> findByNameContainingAndIsDeleteFalse(String name, Pageable pageable);

    Page<ObjectV2Entity> findByIsDeleteFalse(Pageable pageable);

    ObjectV2Entity findByIdAndIsDeleteFalse(String id);
}
