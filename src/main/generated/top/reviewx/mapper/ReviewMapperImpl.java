package top.reviewx.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.entities.review.ReactionEntity;
import top.reviewx.entities.review.ReviewChildEntity;
import top.reviewx.entities.review.ReviewEntity;
import top.reviewx.rest.basic.review.dto.res.ReactionRes;
import top.reviewx.rest.basic.review.dto.res.ReviewChildRes;
import top.reviewx.rest.basic.review.dto.res.ReviewRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-01T18:14:34+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class ReviewMapperImpl extends ReviewMapper {

    @Override
    public ReviewRes toReviewRes(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        ReviewRes.ReviewResBuilder<?, ?> reviewRes = ReviewRes.builder();

        reviewRes.id( reviewEntity.getId() );
        reviewRes.createdAt( map( reviewEntity.getCreatedAt() ) );
        reviewRes.updatedAt( map( reviewEntity.getUpdatedAt() ) );
        reviewRes.objectId( reviewEntity.getObjectId() );
        reviewRes.rate( reviewEntity.getRate() );
        reviewRes.content( reviewEntity.getContent() );
        reviewRes.reactions( reactionEntityListToReactionResList( reviewEntity.getReactions() ) );
        reviewRes.createdBy( reviewEntity.getCreatedBy() );
        reviewRes.childs( reviewChildEntityListToReviewChildResList( reviewEntity.getChilds() ) );

        return reviewRes.build();
    }

    protected ReactionRes reactionEntityToReactionRes(ReactionEntity reactionEntity) {
        if ( reactionEntity == null ) {
            return null;
        }

        ReactionRes.ReactionResBuilder<?, ?> reactionRes = ReactionRes.builder();

        reactionRes.id( reactionEntity.getId() );
        reactionRes.name( reactionEntity.getName() );
        reactionRes.avatar( reactionEntity.getAvatar() );
        reactionRes.isLike( reactionEntity.getIsLike() );
        reactionRes.createdAt( reactionEntity.getCreatedAt() );
        reactionRes.updatedAt( reactionEntity.getUpdatedAt() );

        return reactionRes.build();
    }

    protected List<ReactionRes> reactionEntityListToReactionResList(List<ReactionEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ReactionRes> list1 = new ArrayList<ReactionRes>( list.size() );
        for ( ReactionEntity reactionEntity : list ) {
            list1.add( reactionEntityToReactionRes( reactionEntity ) );
        }

        return list1;
    }

    protected List<ReviewChildRes> reviewChildEntityListToReviewChildResList(List<ReviewChildEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewChildRes> list1 = new ArrayList<ReviewChildRes>( list.size() );
        for ( ReviewChildEntity reviewChildEntity : list ) {
            list1.add( reviewChildEntityToReviewChildRes( reviewChildEntity ) );
        }

        return list1;
    }

    protected ReviewChildRes reviewChildEntityToReviewChildRes(ReviewChildEntity reviewChildEntity) {
        if ( reviewChildEntity == null ) {
            return null;
        }

        ReviewChildRes.ReviewChildResBuilder<?, ?> reviewChildRes = ReviewChildRes.builder();

        reviewChildRes.id( reviewChildEntity.getId() );
        reviewChildRes.createdAt( map( reviewChildEntity.getCreatedAt() ) );
        reviewChildRes.updatedAt( map( reviewChildEntity.getUpdatedAt() ) );
        reviewChildRes.content( reviewChildEntity.getContent() );
        reviewChildRes.createdBy( reviewChildEntity.getCreatedBy() );
        reviewChildRes.reactions( reactionEntityListToReactionResList( reviewChildEntity.getReactions() ) );
        reviewChildRes.childs( reviewChildEntityListToReviewChildResList( reviewChildEntity.getChilds() ) );

        return reviewChildRes.build();
    }
}
