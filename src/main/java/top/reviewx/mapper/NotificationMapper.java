package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.rest.user.notification.dto.res.NotificationRes;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper implements BaseCustomMapper {

    public abstract NotificationRes toNotificationRes(NotificationEntity notificationEntity);
}
