package top.reviewx.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.entities.category.CategoryChildEntity;
import top.reviewx.entities.category.CategoryEntity;
import top.reviewx.rest.basic.category.dto.CategoryChildRes;
import top.reviewx.rest.basic.category.dto.CategoryRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-02T02:10:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class CategoryMapperImpl extends CategoryMapper {

    @Override
    public CategoryRes toCategoryRes(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryRes.CategoryResBuilder<?, ?> categoryRes = CategoryRes.builder();

        categoryRes.id( categoryEntity.getId() );
        categoryRes.createdAt( map( categoryEntity.getCreatedAt() ) );
        categoryRes.updatedAt( map( categoryEntity.getUpdatedAt() ) );
        categoryRes.name( categoryEntity.getName() );
        categoryRes.childs( categoryChildEntityListToCategoryChildResList( categoryEntity.getChilds() ) );
        categoryRes.createdBy( categoryEntity.getCreatedBy() );
        categoryRes.updatedBy( categoryEntity.getUpdatedBy() );

        return categoryRes.build();
    }

    @Override
    public CategoryChildRes toCategoryChildRes(CategoryChildEntity categoryChildEntity) {
        if ( categoryChildEntity == null ) {
            return null;
        }

        CategoryChildRes.CategoryChildResBuilder<?, ?> categoryChildRes = CategoryChildRes.builder();

        categoryChildRes.id( categoryChildEntity.getId() );
        categoryChildRes.createdAt( map( categoryChildEntity.getCreatedAt() ) );
        categoryChildRes.updatedAt( map( categoryChildEntity.getUpdatedAt() ) );
        categoryChildRes.name( categoryChildEntity.getName() );
        categoryChildRes.childs( categoryChildEntityListToCategoryChildResList( categoryChildEntity.getChilds() ) );
        categoryChildRes.createdBy( categoryChildEntity.getCreatedBy() );
        categoryChildRes.updatedBy( categoryChildEntity.getUpdatedBy() );

        return categoryChildRes.build();
    }

    protected List<CategoryChildRes> categoryChildEntityListToCategoryChildResList(List<CategoryChildEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryChildRes> list1 = new ArrayList<CategoryChildRes>( list.size() );
        for ( CategoryChildEntity categoryChildEntity : list ) {
            list1.add( toCategoryChildRes( categoryChildEntity ) );
        }

        return list1;
    }
}
