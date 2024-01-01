package top.reviewx.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.rest.user.notification.dto.res.NotificationRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-02T02:10:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class NotificationMapperImpl extends NotificationMapper {

    @Override
    public NotificationRes toNotificationRes(NotificationEntity notificationEntity) {
        if ( notificationEntity == null ) {
            return null;
        }

        NotificationRes.NotificationResBuilder<?, ?> notificationRes = NotificationRes.builder();

        notificationRes.id( notificationEntity.getId() );
        notificationRes.createdAt( map( notificationEntity.getCreatedAt() ) );
        notificationRes.updatedAt( map( notificationEntity.getUpdatedAt() ) );
        List<String> list = notificationEntity.getReceiver();
        if ( list != null ) {
            notificationRes.receiver( new ArrayList<String>( list ) );
        }
        notificationRes.objectV1Id( notificationEntity.getObjectV1Id() );
        notificationRes.reviewId( notificationEntity.getReviewId() );
        notificationRes.content( notificationEntity.getContent() );
        notificationRes.isRead( notificationEntity.getIsRead() );

        return notificationRes.build();
    }
}
