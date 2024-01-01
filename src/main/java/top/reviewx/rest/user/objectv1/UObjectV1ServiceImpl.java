package top.reviewx.rest.user.objectv1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.base.BaseEntity;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.core.enums.RoleEnum;
import top.reviewx.core.exception.AccessDeniedException;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.entities.object1.NoteEntity;
import top.reviewx.entities.object1.ObjectV1Entity;
import top.reviewx.mapper.ObjectV1Mapper;
import top.reviewx.rest.user.notification.UNotificationRepository;
import top.reviewx.rest.user.objectv1.dto.req.CreateObjectV1Req;
import top.reviewx.rest.user.objectv1.dto.req.UpdateObjectV1UserReq;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;
import top.reviewx.rest.user.user.UUserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UObjectV1ServiceImpl implements UObjectV1Service {
    private final UObjectV1Repository uObjectV1Repository;
    private final UNotificationRepository uNotificationRepository;
    private final UUserRepository uUserRepository;
    private final CommonAuthContext authContext;
    private final ObjectV1Mapper objectV1Mapper;

    @Override
    public ObjectV1Res createObjectV1(CreateObjectV1Req req) {
        String id = UUID.randomUUID().toString();
        uNotificationRepository.insert(NotificationEntity.builder()
                .id(UUID.randomUUID().toString())
                .receiver(uUserRepository.findByRoles(Collections.singletonList(RoleEnum.ADMIN)).stream().map(BaseEntity::getId).collect(Collectors.toList()))
                .objectV1Id(id)
                .reviewId(null)
                .content(authContext.getName() + " đã tạo đề xuất")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        return objectV1Mapper.toObjectV1Res(uObjectV1Repository.insert(ObjectV1Entity.builder()
                .id(id)
                .objectId(req.getObjectId())
                .categoryId(req.getCategoryId())
                .name(req.getName())
                .avatar(req.getAvatar())
                .address(req.getAddress())
                .status(ObjectV1StatusEnum.NEW)
                .isDelete(false)
                .notes(Collections.singletonList(NoteEntity.builder()
                        .id(authContext.getId())
                        .name(authContext.getName())
                        .avatar(authContext.getAvatar())
                        .content(req.getNote())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()))
                .createdBy(BaseCreatedBy.builder()
                        .id(authContext.getId())
                        .name(authContext.getName())
                        .avatar(authContext.getAvatar())
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()));
    }

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ObjectV1Res> getListObjectV1User(String categoryId, String objectId, String name, Pageable pageable) {
        Page<ObjectV1Entity> objectV1EntityPage;
        if (StringUtils.hasText(categoryId) && StringUtils.hasText(name) && StringUtils.hasText(objectId)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndCategoryIdAndObjectIdAndNameContainingAndIsDeleteFalse(authContext.getId(),
                    categoryId,
                    objectId,
                    name,
                    pageable);
        } else if (StringUtils.hasText(categoryId) && StringUtils.hasText(objectId)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndCategoryIdAndObjectIdAndIsDeleteFalse(authContext.getId(),
                    categoryId,
                    objectId,
                    pageable);
        } else if (StringUtils.hasText(categoryId) && StringUtils.hasText(name)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndCategoryIdAndNameContainingAndIsDeleteFalse(authContext.getId(),
                    categoryId,
                    name,
                    pageable);
        } else if (StringUtils.hasText(objectId) && StringUtils.hasText(name)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndObjectIdAndNameContainingAndIsDeleteFalse(authContext.getId(),
                    objectId,
                    name,
                    pageable);
        } else if (StringUtils.hasText(categoryId)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndCategoryIdAndIsDeleteFalse(authContext.getId(),
                    categoryId,
                    pageable);
        } else if (StringUtils.hasText(objectId)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndObjectIdAndIsDeleteFalse(authContext.getId(),
                    objectId,
                    pageable);
        } else if (StringUtils.hasText(name)) {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndNameContainingAndIsDeleteFalse(authContext.getId(),
                    name,
                    pageable);
        } else {
            objectV1EntityPage = uObjectV1Repository.findByCreatedBy_IdAndIsDeleteFalse(authContext.getId(), pageable);
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
    public ObjectV1Res updateObjectV1User(String id, UpdateObjectV1UserReq req) {
        ObjectV1Entity objectV1Entity = uObjectV1Repository.findById(id).orElse(null);
        if (objectV1Entity == null) {
            throw new BusinessLogicException();
        }
        if (!objectV1Entity.getCreatedBy().getId().equals(authContext.getId())) {
            throw new AccessDeniedException();
        }
        if (StringUtils.hasText(req.getName())) {
            objectV1Entity.setName(req.getName());
            objectV1Entity.setStatus(ObjectV1StatusEnum.NEW);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
        }
        if (StringUtils.hasText(req.getAvatar())) {
            objectV1Entity.setAvatar(req.getAvatar());
            objectV1Entity.setStatus(ObjectV1StatusEnum.NEW);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
        }
        if (StringUtils.hasText(req.getAddress())) {
            objectV1Entity.setAddress(req.getAddress());
            objectV1Entity.setStatus(ObjectV1StatusEnum.NEW);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
        }
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
            objectV1Entity.setStatus(ObjectV1StatusEnum.NEW);
            objectV1Entity.setUpdatedAt(LocalDateTime.now());
        }

        uNotificationRepository.insert(NotificationEntity.builder()
                .id(UUID.randomUUID().toString())
                .receiver(uUserRepository.findByRoles(Collections.singletonList(RoleEnum.ADMIN)).stream().map(BaseEntity::getId).collect(Collectors.toList()))
                .objectV1Id(id)
                .reviewId(null)
                .content(authContext.getName() + " đã chỉnh sửa đề xuất")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        return objectV1Mapper.toObjectV1Res(uObjectV1Repository.save(objectV1Entity));
    }

    @Override
    public void deleteObjectV1User(String id) {
        ObjectV1Entity objectV1Entity = uObjectV1Repository.findById(id).orElse(null);
        if (objectV1Entity == null) {
            throw new BusinessLogicException();
        }
        if (!objectV1Entity.getCreatedBy().getId().equals(authContext.getId())) {
            throw new AccessDeniedException();
        }
        objectV1Entity.setIsDelete(true);
        uObjectV1Repository.save(objectV1Entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectV1Res getOneObjectV1User(String id) {
        ObjectV1Entity objectV1Entity = uObjectV1Repository.findByIdAndCreatedBy_IdAndIsDeleteFalse(id, authContext.getId());
        if (objectV1Entity == null) {
            throw new BusinessLogicException();
        }
        return objectV1Mapper.toObjectV1Res(objectV1Entity);
    }
}