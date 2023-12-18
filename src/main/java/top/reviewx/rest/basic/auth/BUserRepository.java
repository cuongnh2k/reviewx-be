package top.reviewx.rest.basic.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.user.UserEntity;

@Repository
public interface BUserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByLocal_Email(String email);
}
