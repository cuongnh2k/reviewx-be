package top.reviewx.rest.admin.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.object1.ObjectV1Entity;

@Repository
public interface AObjectV1Repository extends MongoRepository<ObjectV1Entity, String> {

    Page<ObjectV1Entity> findByCategoryIdAndObjectIdAndNameContaining(String categoryId,
                                                                      String objectId,
                                                                      String name,
                                                                      Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndNameContaining(String categoryId,
                                                           String name,
                                                           Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndObjectId(String categoryId,
                                                     String objectId,
                                                     Pageable pageable);

    Page<ObjectV1Entity> findByObjectIdAndNameContaining(String objectId,
                                                         String name,
                                                         Pageable pageable);

    Page<ObjectV1Entity> findByCategoryId(String categoryId, Pageable pageable);

    Page<ObjectV1Entity> findByObjectId(String objectId, Pageable pageable);

    Page<ObjectV1Entity> findByNameContaining(String name, Pageable pageable);
}
