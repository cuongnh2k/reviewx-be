package top.reviewx.rest.user.notification;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.rest.user.notification.dto.res.NotificationRes;

public interface UNotificationService {
    CommonListResponse<NotificationRes> getListNotification(Pageable pageable);

    void read(String id);
}
