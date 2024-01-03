package top.reviewx.rest.user.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.entities.object1.ObjectV1Entity;

@Repository
public interface UObjectV1Repository extends MongoRepository<ObjectV1Entity, String> {

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectIdAndNameContainingAndStatusAndIsDeleteFalse(String createdById,
                                                                                                              String categoryId,
                                                                                                              String objectId,
                                                                                                              String name,
                                                                                                              ObjectV1StatusEnum status,
                                                                                                              Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndNameContainingAndStatusAndIsDeleteFalse(String createdById,
                                                                                                   String categoryId,
                                                                                                   String name,
                                                                                                   ObjectV1StatusEnum status,
                                                                                                   Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectIdAndStatusAndIsDeleteFalse(String createdById,
                                                                                             String categoryId,
                                                                                             String objectId,
                                                                                             ObjectV1StatusEnum status,
                                                                                             Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectIdAndNameContainingAndStatusAndIsDeleteFalse(String createdById,
                                                                                                 String cobjectId,
                                                                                                 String name,
                                                                                                 ObjectV1StatusEnum status,
                                                                                                 Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndStatusAndIsDeleteFalse(String createdById, String categoryId, ObjectV1StatusEnum status, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectIdAndStatusAndIsDeleteFalse(String createdById, String objectId, ObjectV1StatusEnum status, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndNameContainingAndStatusAndIsDeleteFalse(String createdById, String name, ObjectV1StatusEnum status, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndStatusAndIsDeleteFalse(String createdById, ObjectV1StatusEnum status, Pageable pageable);

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
