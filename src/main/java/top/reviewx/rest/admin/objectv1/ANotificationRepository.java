package top.reviewx.rest.admin.objectv1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.reviewx.entities.notification.NotificationEntity;

import java.util.List;

@Repository
public interface ANotificationRepository extends MongoRepository<NotificationEntity, String> {
}
