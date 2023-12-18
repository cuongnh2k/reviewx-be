package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.object1.ObjectV1Entity;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;

@Mapper(componentModel = "spring")
public abstract class ObjectV1Mapper implements BaseCustomMapper {

    public abstract ObjectV1Res toObjectV1Res(ObjectV1Entity objectV1Entity);
}
