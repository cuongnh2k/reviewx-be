package top.reviewx.rest.user.file;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.file.FileEntity;

@Repository
public interface UFileRepository extends MongoRepository<FileEntity, String> {
}
