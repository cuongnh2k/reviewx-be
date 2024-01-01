package top.reviewx.rest.basic.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.mapper.ReviewMapper;
import top.reviewx.core.utils.BeanCopyUtil;
import top.reviewx.entities.review.ReviewEntity;
import top.reviewx.rest.basic.review.dto.res.ReviewRes;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BReviewServiceImpl implements BReviewService {
    private final BReviewRepository bReviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ReviewRes> getListReview(String objectId, Integer rate, Pageable pageable) {
        Page<ReviewEntity> reviewEntityPage;
        if (rate != null) {
            reviewEntityPage = bReviewRepository.findByObjectIdAndRateAndIsDeleteFalse(objectId, rate, pageable);
        } else {
            reviewEntityPage = bReviewRepository.findByObjectIdAndIsDeleteFalse(objectId, pageable);
        }
        return CommonListResponse.<ReviewRes>builder()
                .content(reviewEntityPage.getContent().stream()
                        .map(reviewMapper::toReviewRes)
                        .collect(Collectors.toList()))
                .totalElements(reviewEntityPage.getTotalElements())
                .totalPages(reviewEntityPage.getTotalPages())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewRes getOneReview(String id) {
        ReviewEntity reviewEntity = bReviewRepository.findByIdAndIsDeleteFalse(id);
        if (reviewEntity == null) {
            throw new BusinessLogicException();
        }
        return reviewMapper.toReviewRes(reviewEntity);
    }
}
