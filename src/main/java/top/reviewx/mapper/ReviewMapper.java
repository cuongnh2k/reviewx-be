package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.review.ReviewEntity;
import top.reviewx.rest.basic.review.dto.res.ReviewRes;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper implements BaseCustomMapper {
    public abstract ReviewRes toReviewRes(ReviewEntity reviewEntity);
}
