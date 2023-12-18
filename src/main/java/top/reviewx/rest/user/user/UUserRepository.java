package top.reviewx.rest.user.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.core.enums.RoleEnum;
import top.reviewx.entities.user.UserEntity;

import java.util.List;

@Repository
public interface UUserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByLocal_Email(String email);

    List<UserEntity> findByRoles(List<RoleEnum> role);
}
