package top.reviewx.rest.user.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.mapper.NotificationMapper;
import top.reviewx.rest.user.notification.dto.res.NotificationRes;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UNotificationServiceImpl implements UNotificationService {
    private final UNotificationRepository uNotificationRepository;
    private final CommonAuthContext authContext;

    private final NotificationMapper notificationMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<NotificationRes> getListNotification(Pageable pageable) {
        Page<NotificationEntity> notificationEntityPage = uNotificationRepository.findByReceiver(Collections.singletonList(authContext.getId()), pageable);
        return CommonListResponse.<NotificationRes>builder()
                .content(notificationEntityPage.getContent().stream()
                        .map(notificationMapper::toNotificationRes)
                        .collect(Collectors.toList()))
                .totalElements(notificationEntityPage.getTotalElements())
                .totalPages(notificationEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    @Override
    public void read(String id) {
        NotificationEntity notificationEntity = uNotificationRepository.findById(id).orElseThrow(BusinessLogicException::new);
        notificationEntity.setIsRead(true);
        uNotificationRepository.save(notificationEntity);
    }
}