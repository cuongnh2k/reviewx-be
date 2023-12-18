package top.reviewx.rest.user.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.exception.AccessDeniedException;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.notification.NotificationEntity;
import top.reviewx.entities.review.ReactionEntity;
import top.reviewx.entities.review.ReviewChildEntity;
import top.reviewx.entities.review.ReviewEntity;
import top.reviewx.mapper.ReviewMapper;
import top.reviewx.rest.basic.review.dto.res.ReviewRes;
import top.reviewx.rest.user.notification.UNotificationRepository;
import top.reviewx.rest.user.review.dto.req.CreateCommentReq;
import top.reviewx.rest.user.review.dto.req.CreateReactionReq;
import top.reviewx.rest.user.review.dto.req.CreateReviewReq;
import top.reviewx.rest.user.review.dto.req.UpdateCommentReq;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UReviewServiceImpl implements UReviewService {
    private final UReviewRepository uReviewRepository;
    private final UNotificationRepository uNotificationRepository;
    private final CommonAuthContext authContext;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewRes createOrUpdateReview(CreateReviewReq req) {
        ReviewEntity reviewEntity = uReviewRepository.findByObjectIdAndCreatedBy_IdAndIsDeleteFalse(req.getObjectId(),
                authContext.getId());
        if (reviewEntity != null) {
            reviewEntity.setRate(req.getRate());
            reviewEntity.setContent(req.getContent());
            reviewEntity.setUpdatedAt(LocalDateTime.now());
        } else {
            reviewEntity = ReviewEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .objectId(req.getObjectId())
                    .rate(req.getRate())
                    .content(req.getContent())
                    .createdBy(BaseCreatedBy.builder()
                            .id(authContext.getId())
                            .name(authContext.getName())
                            .name(authContext.getAvatar())
                            .build())
                    .isDelete(false)
                    .build();
        }
        return reviewMapper.toReviewRes(uReviewRepository.save(reviewEntity));
    }

    @Override
    public String deleteReview(String objectId) {
        ReviewEntity reviewEntity = uReviewRepository.findByObjectIdAndCreatedBy_IdAndIsDeleteFalse(objectId,
                authContext.getId());
        if (reviewEntity != null) {
            reviewEntity.setIsDelete(true);
            reviewEntity.setUpdatedAt(LocalDateTime.now());
            uReviewRepository.save(reviewEntity);
        }
        return objectId;
    }

    @Override
    public ReviewRes createOrUpdateReaction(String id, CreateReactionReq req) {
        String[] ids;
        if (id.length() > 36) {
            ids = id.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = id;
        }
        ReviewEntity reviewEntity = uReviewRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (reviewEntity == null) {
            throw new BusinessLogicException();
        }
        switch (ids.length) {
            case 1 -> {
                if (reviewEntity.getReactions() == null) {
                    reviewEntity.setReactions(Collections.singletonList(ReactionEntity.builder()
                            .id(authContext.getId())
                            .name(authContext.getName())
                            .avatar(authContext.getAvatar())
                            .isLike(req.getIsLike())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()));
                } else if (reviewEntity.getReactions().stream().noneMatch(o -> o.getId().equals(authContext.getId()))) {
                    reviewEntity.getReactions().add(ReactionEntity.builder()
                            .id(authContext.getId())
                            .name(authContext.getName())
                            .avatar(authContext.getAvatar())
                            .isLike(req.getIsLike())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                } else {
                    reviewEntity.getReactions().forEach(o -> {
                        if (o.getId().equals(authContext.getId()) && o.getIsLike() != req.getIsLike()) {
                            o.setIsLike(req.getIsLike());
                            o.setId(authContext.getId());
                            o.setName(authContext.getName());
                            o.setAvatar(authContext.getAvatar());
                            o.setUpdatedAt(LocalDateTime.now());
                        }
                    });
                }

                uNotificationRepository.insert(NotificationEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .receiver(Collections.singletonList(reviewEntity.getCreatedBy().getId()))
                        .objectV1Id(null)
                        .reviewId(id)
                        .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                        .isRead(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
            case 2 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getReactions() == null) {
                    reviewEntity.getChilds().forEach(o -> {
                                if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                                    o.setReactions(Collections.singletonList(ReactionEntity.builder()
                                            .id(authContext.getId())
                                            .name(authContext.getName())
                                            .avatar(authContext.getAvatar())
                                            .isLike(req.getIsLike())
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build()));

                                    uNotificationRepository.insert(NotificationEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                            .objectV1Id(null)
                                            .reviewId(id)
                                            .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                            .isRead(false)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());
                                }
                            }
                    );
                } else if (reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getReactions().stream().noneMatch(o -> o.getId().equals(authContext.getId()))) {
                    reviewEntity.getChilds().forEach(o -> {
                                if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                                    o.getReactions().add(ReactionEntity.builder()
                                            .id(authContext.getId())
                                            .name(authContext.getName())
                                            .avatar(authContext.getAvatar())
                                            .isLike(req.getIsLike())
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());

                                    uNotificationRepository.insert(NotificationEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                            .objectV1Id(null)
                                            .reviewId(id)
                                            .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                            .isRead(false)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());
                                }
                            }
                    );
                } else {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.getReactions().forEach(oo -> {
                                if (oo.getId().equals(authContext.getId()) && oo.getIsLike() != req.getIsLike()) {
                                    oo.setIsLike(req.getIsLike());
                                    oo.setId(authContext.getId());
                                    oo.setName(authContext.getName());
                                    oo.setAvatar(authContext.getAvatar());
                                    oo.setUpdatedAt(LocalDateTime.now());

                                    uNotificationRepository.insert(NotificationEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                            .objectV1Id(null)
                                            .reviewId(id)
                                            .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                            .isRead(false)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());
                                }
                            });
                        }
                    });
                }
            }
            case 3 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().noneMatch(o -> o.getId().equals(ids[2]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().filter(o -> o.getId().equals(ids[2]) && !o.getIsDelete()).findFirst().get().getReactions() == null) {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.getChilds().forEach(oo -> {
                                if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                    oo.setReactions(Collections.singletonList(ReactionEntity.builder()
                                            .id(authContext.getId())
                                            .name(authContext.getName())
                                            .avatar(authContext.getAvatar())
                                            .isLike(req.getIsLike())
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build()));

                                    uNotificationRepository.insert(NotificationEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .receiver(Collections.singletonList(oo.getCreatedBy().getId()))
                                            .objectV1Id(null)
                                            .reviewId(id)
                                            .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                            .isRead(false)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());
                                }
                            });
                        }
                    });
                } else if (reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().filter(o -> o.getId().equals(ids[2]) && !o.getIsDelete()).findFirst().get().getReactions().stream().noneMatch(o -> o.getId().equals(authContext.getId()))) {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.getChilds().forEach(oo -> {
                                if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                    oo.getReactions().add((ReactionEntity.builder()
                                            .id(authContext.getId())
                                            .name(authContext.getName())
                                            .avatar(authContext.getAvatar())
                                            .isLike(req.getIsLike())
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build()));

                                    uNotificationRepository.insert(NotificationEntity.builder()
                                            .id(UUID.randomUUID().toString())
                                            .receiver(Collections.singletonList(oo.getCreatedBy().getId()))
                                            .objectV1Id(null)
                                            .reviewId(id)
                                            .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                            .isRead(false)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build());
                                }
                            });
                        }
                    });
                } else {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.getChilds().forEach(oo -> {
                                if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                    oo.getReactions().forEach(ooo -> {
                                        if (ooo.getId().equals(authContext.getId()) && ooo.getIsLike() != req.getIsLike()) {
                                            ooo.setIsLike(req.getIsLike());
                                            ooo.setId(authContext.getId());
                                            ooo.setName(authContext.getName());
                                            ooo.setAvatar(authContext.getAvatar());
                                            ooo.setUpdatedAt(LocalDateTime.now());

                                            uNotificationRepository.insert(NotificationEntity.builder()
                                                    .id(UUID.randomUUID().toString())
                                                    .receiver(Collections.singletonList(oo.getCreatedBy().getId()))
                                                    .objectV1Id(null)
                                                    .reviewId(id)
                                                    .content(authContext.getName() + (req.getIsLike() ? " thích" : " chê") + " phản hồi của bạn")
                                                    .isRead(false)
                                                    .createdAt(LocalDateTime.now())
                                                    .updatedAt(LocalDateTime.now())
                                                    .build());
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
            default -> throw new BusinessLogicException();
        }
        return reviewMapper.toReviewRes(uReviewRepository.save(reviewEntity));
    }

    @Override
    public ReviewRes createComment(String id, CreateCommentReq req) {
        String[] ids;
        if (id.length() > 36) {
            ids = id.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = id;
        }
        ReviewEntity reviewEntity = uReviewRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (reviewEntity == null) {
            throw new BusinessLogicException();
        }
        BaseCreatedBy createdBy = BaseCreatedBy.builder()
                .id(authContext.getId())
                .name(authContext.getName())
                .avatar(authContext.getAvatar())
                .build();
        switch (ids.length) {
            case 1 -> {
                if (reviewEntity.getChilds() == null) {
                    reviewEntity.setChilds(Collections.singletonList(ReviewChildEntity.builder()
                            .id(UUID.randomUUID().toString())
                            .content(req.getContent())
                            .createdBy(createdBy)
                            .isDelete(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()));
                } else {
                    reviewEntity.getChilds().add(ReviewChildEntity.builder()
                            .id(UUID.randomUUID().toString())
                            .content(req.getContent())
                            .createdBy(createdBy)
                            .isDelete(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
                }

                uNotificationRepository.insert(NotificationEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .receiver(Collections.singletonList(reviewEntity.getCreatedBy().getId()))
                        .objectV1Id(null)
                        .reviewId(id)
                        .content(authContext.getName() + " đã thêm phản hồi")
                        .isRead(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }
            case 2 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null) {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.setChilds(Collections.singletonList(ReviewChildEntity.builder()
                                    .id(UUID.randomUUID().toString())
                                    .content(req.getContent())
                                    .createdBy(createdBy)
                                    .isDelete(false)
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build()));

                            uNotificationRepository.insert(NotificationEntity.builder()
                                    .id(UUID.randomUUID().toString())
                                    .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                    .objectV1Id(null)
                                    .reviewId(id)
                                    .content(authContext.getName() + " đã thêm phản hồi")
                                    .isRead(false)
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build());
                        }
                    });
                } else {
                    reviewEntity.getChilds().forEach(o -> {
                        if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                            o.getChilds().add(ReviewChildEntity.builder()
                                    .id(UUID.randomUUID().toString())
                                    .content(req.getContent())
                                    .createdBy(createdBy)
                                    .isDelete(false)
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build());

                            uNotificationRepository.insert(NotificationEntity.builder()
                                    .id(UUID.randomUUID().toString())
                                    .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                    .objectV1Id(null)
                                    .reviewId(id)
                                    .content(authContext.getName() + " đã thêm phản hồi")
                                    .isRead(false)
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build());
                        }
                    });
                }
            }
            default -> throw new BusinessLogicException();
        }
        return reviewMapper.toReviewRes(uReviewRepository.save(reviewEntity));
    }

    @Override
    public ReviewRes updateComment(String id, UpdateCommentReq req) {
        String[] ids;
        if (id.length() > 36) {
            ids = id.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = id;
        }
        ReviewEntity reviewEntity = uReviewRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (reviewEntity == null) {
            throw new BusinessLogicException();
        }
        switch (ids.length) {
            case 1 -> {
                if (!reviewEntity.getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.setContent(req.getContent());
                reviewEntity.setUpdatedAt(LocalDateTime.now());
            }
            case 2 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (!reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.setContent(req.getContent());
                        o.setUpdatedAt(LocalDateTime.now());

                        uNotificationRepository.insert(NotificationEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .receiver(Collections.singletonList(reviewEntity.getCreatedBy().getId()))
                                .objectV1Id(null)
                                .reviewId(id)
                                .content(authContext.getName() + " đã chỉnh sửa phản hồi")
                                .isRead(false)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build());
                    }
                });
            }
            case 3 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().noneMatch(o -> o.getId().equals(ids[2]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (!reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().filter(o -> o.getId().equals(ids[2]) && !o.getIsDelete()).findFirst().get().getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.getChilds().forEach(oo -> {
                            if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                oo.setContent(req.getContent());
                                oo.setUpdatedAt(LocalDateTime.now());

                                uNotificationRepository.insert(NotificationEntity.builder()
                                        .id(UUID.randomUUID().toString())
                                        .receiver(Collections.singletonList(o.getCreatedBy().getId()))
                                        .objectV1Id(null)
                                        .reviewId(id)
                                        .content(authContext.getName() + " đã chỉnh sửa phản hồi")
                                        .isRead(false)
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .build());
                            }
                        });
                    }
                });
            }
            default -> throw new BusinessLogicException();
        }
        return reviewMapper.toReviewRes(uReviewRepository.save(reviewEntity));
    }

    @Override
    public String deleteComment(String id) {
        String[] ids;
        if (id.length() > 36) {
            ids = id.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = id;
        }
        ReviewEntity reviewEntity = uReviewRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (reviewEntity == null) {
            throw new BusinessLogicException();
        }
        switch (ids.length) {
            case 1 -> {
                if (!reviewEntity.getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.setIsDelete(true);
                reviewEntity.setUpdatedAt(LocalDateTime.now());
            }
            case 2 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (!reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        reviewEntity.setIsDelete(true);
                        o.setUpdatedAt(LocalDateTime.now());
                    }
                });
            }
            case 3 -> {
                if (reviewEntity.getChilds() == null
                        || reviewEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null
                        || reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().noneMatch(o -> o.getId().equals(ids[2]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                if (!reviewEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().filter(o -> o.getId().equals(ids[2]) && !o.getIsDelete()).findFirst().get().getCreatedBy().getId().equals(authContext.getId())) {
                    throw new AccessDeniedException();
                }
                reviewEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.getChilds().forEach(oo -> {
                            if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                reviewEntity.setIsDelete(true);
                                oo.setUpdatedAt(LocalDateTime.now());
                            }
                        });
                    }
                });
            }
            default -> throw new BusinessLogicException();
        }
        uReviewRepository.save(reviewEntity);
        return id;
    }
}
