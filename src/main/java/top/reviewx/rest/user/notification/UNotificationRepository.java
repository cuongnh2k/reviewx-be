package top.reviewx.rest.user.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.notification.NotificationEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface UNotificationRepository extends MongoRepository<NotificationEntity, String> {
    Page<NotificationEntity> findByReceiver(List<String> receiver, Pageable pageable);
}
