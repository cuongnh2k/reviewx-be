package top.reviewx.rest.admin.objectv1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.entities.object1.NoteEntity;
import top.reviewx.entities.object1.ObjectV1Entity;
import top.reviewx.entities.object2.ObjectV2Entity;
import top.reviewx.mapper.ObjectV1Mapper;
import top.reviewx.rest.admin.objectv1.dto.req.UpdateObjectV1AdminReq;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AObjectV1ServiceImpl implements AObjectV1Service {
    private final AObjectV1Repository aObjectV1Repository;
    private final AObjectV2Repository aObjectV2Repository;
    private final ANotificationRepository aNotificationRepository;
    private final CommonAuthContext authContext;
    private final ObjectV1Mapper objectV1Mapper;


    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ObjectV1Res> getListObjectV1Admin(String categoryId, String objectId, String name, ObjectV1StatusEnum status, Pageable pageable) {
        Page<ObjectV1Entity> objectV1EntityPage;
        if (StringUtils.hasText(categoryId) && StringUtils.hasText(name) && StringUtils.hasText(objectId)) {
            objectV1EntityPage = aObjectV1Repository.findByCategoryIdAndObjectIdAndNameContainingAndStatus(
                    categoryId,
                    objectId,
                    name,
                    status,
                    pageable);
        } else if (StringUtils.hasText(categoryId) && StringUtils.hasText(objectId)) {
            objectV1EntityPage = aObjectV1Repository.findByCategoryIdAndObjectIdAndStatus(
                    categoryId,
                    objectId,
                    status,
                    pageable);
        } else if (StringUtils.hasText(categoryId) && StringUtils.hasText(name)) {
            objectV1EntityPage = aObjectV1Repository.findByCategoryIdAndNameContainingAndStatus(
                    categoryId,
                    name,
                    status,
                    pageable);
        } else if (StringUtils.hasText(objectId) && StringUtils.hasText(name)) {
            objectV1EntityPage = aObjectV1Repository.findByObjectIdAndNameContainingAndStatus(
                    objectId,
                    name,
                    status,
                    pageable);
        } else if (StringUtils.hasText(categoryId)) {
            objectV1EntityPage = aObjectV1Repository.findByCategoryIdAndStatus(
                    categoryId,
                    status,
                    pageable);
        } else if (StringUtils.hasText(objectId)) {
            objectV1EntityPage = aObjectV1Repository.findByObjectIdAndStatus(
                    objectId,
                    status,
                    pageable);
        } else if (StringUtils.hasText(name)) {
            objectV1EntityPage = aObjectV1Repository.findByNameContainingAndStatus(
                    name,
                    status,
                    pageable);
        } else {
            objectV1EntityPage = aObjectV1Repository.findByStatus(status, pageable);
        }
        return CommonListResponse.<ObjectV1Res>builder()
                .content(objectV1EntityPage.getContent().stream()
                        .map(objectV1Mapper::toObjectV1Res)
                        .collect(Collectors.toList()))
                .totalElements(objectV1EntityPage.getTotalElements())
                .totalPages(objectV1EntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }


    @Override
    public ObjectV1Res updateObjectV1Admin(String id, UpdateObjectV1AdminReq req) {
        ObjectV1Entity objectV1Entity = aObjectV1Repository.findById(id).orElse(null);
        if (objectV1Entity == null) {
            throw new BusinessLogicException();
        }
        if (req.getStatus() == ObjectV1StatusEnum.SUCCESS) {
            objectV1Entity.setStatus(ObjectV1StatusEnum.SUCCESS);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
            aObjectV1Repository.save(objectV1Entity);
            BaseCreatedBy createdBy = BaseCreatedBy.builder()
                    .id(objectV1Entity.getCreatedBy().getId())
                    .name(objectV1Entity.getCreatedBy().getName())
                    .avatar(objectV1Entity.getCreatedBy().getAvatar())
                    .build();
            if (StringUtils.hasText(objectV1Entity.getObjectId())) {
                ObjectV2Entity objectV2Entity = aObjectV2Repository.findByIdAndIsDeleteFalse(objectV1Entity.getObjectId());
                if (objectV2Entity == null) {
                    throw new BusinessLogicException();
                }
                objectV2Entity.setCategoryId(objectV1Entity.getCategoryId());
                objectV2Entity.setName(objectV1Entity.getName());
                objectV2Entity.setAvatar(objectV1Entity.getAvatar());
                objectV2Entity.setAddress(objectV1Entity.getAddress());
                objectV2Entity.setCreatedBy(createdBy);
                objectV2Entity.setUpdatedAt(LocalDateTime.now());
                aObjectV2Repository.save(objectV2Entity);
            } else {
                aObjectV2Repository.insert(ObjectV2Entity.builder()
                        .id(objectV1Entity.getId())
                        .categoryId(objectV1Entity.getCategoryId())
                        .name(objectV1Entity.getName())
                        .avatar(objectV1Entity.getAvatar())
                        .address(objectV1Entity.getAddress())
                        .createdBy(createdBy)
                        .isDelete(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
        } else if (req.getStatus() == ObjectV1StatusEnum.REJECT) {
            if (StringUtils.hasText(req.getNote())) {
                if (CollectionUtils.isEmpty(objectV1Entity.getNotes())) {
                    objectV1Entity.setNotes(Collections.singletonList(NoteEntity.builder()
                            .id(authContext.getId())
                            .name(authContext.getName())
                            .avatar(authContext.getAvatar())
                            .content(req.getNote())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()));
                } else if (objectV1Entity.getNotes().stream().noneMatch(o -> o.getId().equals(authContext.getId()))) {
                    objectV1Entity.getNotes().add(NoteEntity.builder()
                            .id(authContext.getId())
                            .name(authContext.getName())
                            .avatar(authContext.getAvatar())
                            .content(req.getNote())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                } else {
                    objectV1Entity.getNotes().forEach(o -> {
                        if (o.getId().equals(authContext.getId())) {
                            o.setName(authContext.getName());
                            o.setAvatar(authContext.getAvatar());
                            o.setContent(req.getNote());
                            o.setUpdatedAt(LocalDateTime.now());
                        }
                    });
                }
            }
            objectV1Entity.setStatus(ObjectV1StatusEnum.REJECT);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
            aObjectV1Repository.save(objectV1Entity);
        }

        aNotificationRepository.insert(NotificationEntity.builder()
                .id(UUID.randomUUID().toString())
                .receiver(Collections.singletonList(objectV1Entity.getCreatedBy().getId()))
                .objectV1Id(id)
                .reviewId(null)
                .content(authContext.getName() + " đã phản hồi đề xuất")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        return objectV1Mapper.toObjectV1Res(objectV1Entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectV1Res getOneObjectV1Admin(String id) {
        ObjectV1Entity objectV1Entity = aObjectV1Repository.findById(id).orElse(null);
        if (objectV1Entity == null) {
            throw new BusinessLogicException();
        }
        return objectV1Mapper.toObjectV1Res(objectV1Entity);
    }
}