package top.reviewx.rest.admin.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.entities.object1.ObjectV1Entity;

@Repository
public interface AObjectV1Repository extends MongoRepository<ObjectV1Entity, String> {

    Page<ObjectV1Entity> findByCategoryIdAndObjectIdAndNameContainingAndStatus(String categoryId,
                                                                               String objectId,
                                                                               String name,
                                                                               ObjectV1StatusEnum status,
                                                                               Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndNameContainingAndStatus(String categoryId,
                                                                    String name,
                                                                    ObjectV1StatusEnum status,
                                                                    Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndObjectIdAndStatus(String categoryId,
                                                              String objectId,
                                                              ObjectV1StatusEnum status,
                                                              Pageable pageable);

    Page<ObjectV1Entity> findByObjectIdAndNameContainingAndStatus(String objectId,
                                                                  String name,
                                                                  ObjectV1StatusEnum status,
                                                                  Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndStatus(String categoryId,
                                                   ObjectV1StatusEnum status,
                                                   Pageable pageable);

    Page<ObjectV1Entity> findByObjectIdAndStatus(String objectId,
                                                 ObjectV1StatusEnum status,
                                                 Pageable pageable);

    Page<ObjectV1Entity> findByNameContainingAndStatus(String name,
                                                       ObjectV1StatusEnum status,
                                                       Pageable pageable);

    Page<ObjectV1Entity> findByStatus(ObjectV1StatusEnum status,
                                      Pageable pageable);
}
