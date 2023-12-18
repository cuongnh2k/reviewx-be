package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.category.CategoryChildEntity;
import top.reviewx.entities.category.CategoryEntity;
import top.reviewx.rest.basic.category.dto.CategoryChildRes;
import top.reviewx.rest.basic.category.dto.CategoryRes;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper implements BaseCustomMapper {

    //    @Mapping(target = "demo", ignore = true)
    public abstract CategoryRes toCategoryRes(CategoryEntity categoryEntity);

    public abstract CategoryChildRes toCategoryChildRes(CategoryChildEntity categoryChildEntity);
}
