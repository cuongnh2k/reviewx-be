package top.reviewx.rest.basic.objectv2;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.object2.ObjectV2Entity;
import top.reviewx.entities.review.ReviewEntity;
import top.reviewx.mapper.ObjectV2Mapper;
import top.reviewx.rest.basic.objectv2.dto.res.ObjectV2Res;
import top.reviewx.rest.basic.review.BReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BObjectV2ServiceImpl implements BObjectV2Service {
    private final BObjectV2Repository bObjectV2Repository;
    private final ObjectV2Mapper objectV2Mapper;
    private final BReviewRepository bReviewRepository;

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ObjectV2Res> getListObjectV2(String categoryId, String name, Pageable pageable) {
        Page<ObjectV2Entity> objectV2EntityPage;
        if (StringUtils.hasText(categoryId) && StringUtils.hasText(name)) {
            objectV2EntityPage = bObjectV2Repository.findByCategoryIdAndNameContainingAndIsDeleteFalse(categoryId, name, pageable);
        } else if (StringUtils.hasText(categoryId)) {
            objectV2EntityPage = bObjectV2Repository.findByCategoryIdAndIsDeleteFalse(categoryId, pageable);
        } else if (StringUtils.hasText(name)) {
            objectV2EntityPage = bObjectV2Repository.findByNameContainingAndIsDeleteFalse(name, pageable);
        } else {
            objectV2EntityPage = bObjectV2Repository.findByIsDeleteFalse(pageable);
        }
        return CommonListResponse.<ObjectV2Res>builder()
                .content(objectV2EntityPage.getContent().stream()
                        .map(objectV2Mapper::toObjectV2Res)
                        .collect(Collectors.toList()))
                .totalElements(objectV2EntityPage.getTotalElements())
                .totalPages(objectV2EntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectV2Res getOneObjectV2(String id) {
        ObjectV2Entity objectV2Entity = bObjectV2Repository.findByIdAndIsDeleteFalse(id);
        if (objectV2Entity == null) {
            throw new BusinessLogicException();
        }
        ObjectV2Res objectV2Res = objectV2Mapper.toObjectV2Res(objectV2Entity);

        List<ReviewEntity> reviewEntities = bReviewRepository.findByObjectIdAndIsDeleteFalse(id);
        if (!CollectionUtils.isEmpty(reviewEntities)) {
            float sum = 0;
            for (ReviewEntity o : reviewEntities) {
                sum += o.getRate();
            }
            objectV2Res.setAverageRating(sum / (float) reviewEntities.size());
        }
        return objectV2Res;
    }
}