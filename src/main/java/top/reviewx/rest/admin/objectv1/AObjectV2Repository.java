package top.reviewx.rest.admin.objectv1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.object2.ObjectV2Entity;

@Repository
public interface AObjectV2Repository extends MongoRepository<ObjectV2Entity, String> {

    ObjectV2Entity findByIdAndIsDeleteFalse(String id);
}
