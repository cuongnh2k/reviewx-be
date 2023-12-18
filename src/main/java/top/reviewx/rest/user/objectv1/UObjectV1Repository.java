package top.reviewx.rest.user.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.object1.ObjectV1Entity;

@Repository
public interface UObjectV1Repository extends MongoRepository<ObjectV1Entity, String> {

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectIdAndNameContaining(String createdById,
                                                                                     String categoryId,
                                                                                     String objectId,
                                                                                     String name,
                                                                                     Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndNameContaining(String createdById,
                                                                          String categoryId,
                                                                          String name,
                                                                          Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryIdAndObjectId(String createdById,
                                                                    String categoryId,
                                                                    String objectId,
                                                                    Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectIdAndNameContaining(String createdById,
                                                                        String cobjectId,
                                                                        String name,
                                                                        Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndCategoryId(String createdById, String categoryId, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndObjectId(String createdById, String objectId, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_IdAndNameContaining(String createdById, String name, Pageable pageable);

    Page<ObjectV1Entity> findByCreatedBy_Id(String createdById, Pageable pageable);

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

    Page<ObjectV1Entity> findByCategoryId( String categoryId, Pageable pageable);

    Page<ObjectV1Entity> findByObjectId( String objectId, Pageable pageable);

    Page<ObjectV1Entity> findByNameContaining(String name, Pageable pageable);

    ObjectV1Entity findByIdAndCreatedBy_Id(String id, String createdById);
}
