package top.reviewx.rest.user.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.object1.ObjectV1Entity;

@Repository
public interface UObjectV1Repository extends MongoRepository<ObjectV1Entity, String> {

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectIdAndNameContainingAndIsDeleteFalse(String createdById,
                                                                                                     String categoryId,
                                                                                                     String objectId,
                                                                                                     String name,
                                                                                                     Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndNameContainingAndIsDeleteFalse(String createdById,
                                                                                          String categoryId,
                                                                                          String name,
                                                                                          Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectIdAndIsDeleteFalse(String createdById,
                                                                                    String categoryId,
                                                                                    String objectId,
                                                                                    Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectIdAndNameContainingAndIsDeleteFalse(String createdById,
                                                                        String cobjectId,
                                                                        String name,
                                                                        Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndIsDeleteFalse(String createdById, String categoryId, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectIdAndIsDeleteFalse(String createdById, String objectId, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndNameContainingAndIsDeleteFalse(String createdById, String name, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndIsDeleteFalse(String createdById, Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndObjectIdAndNameContainingAndIsDeleteFalse(String categoryId,
                                                                      String objectId,
                                                                      String name,
                                                                      Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndNameContainingAndIsDeleteFalse(String categoryId,
                                                           String name,
                                                           Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndObjectIdAndIsDeleteFalse(String categoryId,
                                                     String objectId,
                                                     Pageable pageable);

    Page<ObjectV1Entity> findByObjectIdAndNameContainingAndIsDeleteFalse(String objectId,
                                                         String name,
                                                         Pageable pageable);

    Page<ObjectV1Entity> findByCategoryIdAndIsDeleteFalse(String categoryId, Pageable pageable);

    Page<ObjectV1Entity> findByObjectIdAndIsDeleteFalse(String objectId, Pageable pageable);

    Page<ObjectV1Entity> findByNameContainingAndIsDeleteFalse(String name, Pageable pageable);

    ObjectV1Entity findByIdAndCreatedBy_IdAndIsDeleteFalse(String id, String createdById);
}
