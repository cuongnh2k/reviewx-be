package top.reviewx.entities.notification;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reviewx.core.base.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Notification")
@EqualsAndHashCode(callSuper = true)
public class NotificationEntity extends BaseEntity {
    private List<String> receiver;
    private String objectV1Id;
    private String reviewId;
    private String content;
    private Boolean isRead;
}
