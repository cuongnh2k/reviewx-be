package top.reviewx.mapper;

import org.mapstruct.Mapper;
import top.reviewx.core.base.BaseCustomMapper;
import top.reviewx.entities.object2.ObjectV2Entity;
import top.reviewx.rest.basic.objectv2.dto.res.ObjectV2Res;

@Mapper(componentModel = "spring")
public abstract class ObjectV2Mapper implements BaseCustomMapper {

    public abstract ObjectV2Res toObjectV2Res(ObjectV2Entity objectV2Entity);
}
