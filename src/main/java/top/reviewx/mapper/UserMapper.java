package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.rest.user.user.dto.res.UserRes;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements BaseCustomMapper {
    public abstract UserRes toUserRes(UserEntity userEntity);
}
