package top.reviewx.rest.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.user.UserEntity;

@Repository
public interface AUserRepository extends MongoRepository<UserEntity, String> {

    Page<UserEntity> findByLocal_NameAndLocal_IsActive(String name, Boolean isActive, Pageable pageable);

    Page<UserEntity> findByLocal_Name(String name, Pageable pageable);

    Page<UserEntity> findByLocal_IsActive(Boolean isActive, Pageable pageable);
}
