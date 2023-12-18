package top.reviewx.rest.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.reviewx.core.base.BaseCreatedBy;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.mapper.CategoryMapper;
import top.reviewx.entities.category.CategoryChildEntity;
import top.reviewx.entities.category.CategoryEntity;
import top.reviewx.rest.admin.category.dto.req.CategoryCreateReq;
import top.reviewx.rest.admin.category.dto.req.UpdateCategoryReq;
import top.reviewx.rest.basic.category.dto.CategoryRes;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ACategoryServiceImpl implements ACategoryService {
    private final CommonAuthContext authContext;
    private final ACategoryRepository aCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryRes createCategory(CategoryCreateReq req) {
        BaseCreatedBy baseCreatedBy = BaseCreatedBy.builder()
                .id(authContext.getId())
                .name(authContext.getName())
                .avatar(authContext.getAvatar()).build();
        return categoryMapper.toCategoryRes(aCategoryRepository.insert(CategoryEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(req.getName())
                .isDelete(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(baseCreatedBy)
                .updatedBy(baseCreatedBy)
                .childs(CollectionUtils.isEmpty(req.getChilds()) ? null :
                        req.getChilds().stream()
                                .map(o -> CategoryChildEntity.builder()
                                        .id(UUID.randomUUID().toString())
                                        .name(o.getName())
                                        .isDelete(false)
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .createdBy(baseCreatedBy)
                                        .updatedBy(baseCreatedBy)
                                        .childs(CollectionUtils.isEmpty(o.getChilds()) ? null :
                                                o.getChilds().stream()
                                                        .map(oo -> CategoryChildEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .name(oo.getName())
                                                                .isDelete(false)
                                                                .createdAt(LocalDateTime.now())
                                                                .updatedAt(LocalDateTime.now())
                                                                .createdBy(baseCreatedBy)
                                                                .updatedBy(baseCreatedBy)
                                                                .build())
                                                        .collect(Collectors.toList()))
                                        .build())
                                .collect(Collectors.toList()))
                .build()));
    }

    @Override
    public CategoryRes updateCategory(String categoryId, UpdateCategoryReq req) {
        String[] ids;
        if (categoryId.length() > 36) {
            ids = categoryId.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = categoryId;
        }
        CategoryEntity categoryEntity = aCategoryRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (categoryEntity == null) {
            throw new BusinessLogicException();
        }
        BaseCreatedBy createdBy = BaseCreatedBy.builder()
                .id(authContext.getId())
                .name(authContext.getName())
                .avatar(authContext.getAvatar())
                .build();
        switch (ids.length) {
            case 1 -> {
                categoryEntity.setName(req.getName());
                categoryEntity.setUpdatedBy(createdBy);
                categoryEntity.setUpdatedAt(LocalDateTime.now());
            }
            case 2 -> {
                if (CollectionUtils.isEmpty(categoryEntity.getChilds())
                        || categoryEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                categoryEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.setName(req.getName());
                        o.setUpdatedBy(createdBy);
                        o.setUpdatedAt(LocalDateTime.now());
                    }
                });
            }
            case 3 -> {
                if (CollectionUtils.isEmpty(categoryEntity.getChilds())
                        || categoryEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())
                        || categoryEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null
                        || categoryEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().noneMatch(o -> o.getId().equals(ids[2]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                categoryEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.getChilds().forEach(oo -> {
                            if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                oo.setName(req.getName());
                                oo.setUpdatedBy(createdBy);
                                oo.setUpdatedAt(LocalDateTime.now());
                            }
                        });
                    }
                });
            }
            default -> throw new BusinessLogicException();
        }
        return categoryMapper.toCategoryRes(aCategoryRepository.save(categoryEntity));
    }

    @Override
    public String deleteCategory(String categoryId) {
        String[] ids;
        if (categoryId.length() > 36) {
            ids = categoryId.split("\\.");
        } else {
            ids = new String[1];
            ids[0] = categoryId;
        }
        CategoryEntity categoryEntity = aCategoryRepository.findByIdAndIsDeleteFalse(ids[0]);
        if (categoryEntity == null) {
            throw new BusinessLogicException();
        }
        BaseCreatedBy createdBy = BaseCreatedBy.builder()
                .id(authContext.getId())
                .name(authContext.getName())
                .avatar(authContext.getAvatar())
                .build();
        switch (ids.length) {
            case 1 -> {
                categoryEntity.setIsDelete(true);
                categoryEntity.setUpdatedBy(createdBy);
                categoryEntity.setUpdatedAt(LocalDateTime.now());
            }
            case 2 -> {
                if (CollectionUtils.isEmpty(categoryEntity.getChilds())
                        || categoryEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                categoryEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.setIsDelete(true);
                        o.setUpdatedBy(createdBy);
                        o.setUpdatedAt(LocalDateTime.now());
                    }
                });
            }
            case 3 -> {
                if (CollectionUtils.isEmpty(categoryEntity.getChilds())
                        || categoryEntity.getChilds().stream().noneMatch(o -> o.getId().equals(ids[1]) && !o.getIsDelete())
                        || categoryEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds() == null
                        || categoryEntity.getChilds().stream().filter(o -> o.getId().equals(ids[1]) && !o.getIsDelete()).findFirst().get().getChilds().stream().noneMatch(o -> o.getId().equals(ids[2]) && !o.getIsDelete())) {
                    throw new BusinessLogicException();
                }
                categoryEntity.getChilds().forEach(o -> {
                    if (o.getId().equals(ids[1]) && !o.getIsDelete()) {
                        o.getChilds().forEach(oo -> {
                            if (oo.getId().equals(ids[2]) && !oo.getIsDelete()) {
                                oo.setIsDelete(true);
                                oo.setUpdatedBy(createdBy);
                                oo.setUpdatedAt(LocalDateTime.now());
                            }
                        });
                    }
                });
            }
            default -> throw new BusinessLogicException();
        }
        aCategoryRepository.save(categoryEntity);
        return categoryId;
    }
}
