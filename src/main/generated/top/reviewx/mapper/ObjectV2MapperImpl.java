package top.reviewx.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.entities.object2.ObjectV2Entity;
import top.reviewx.rest.basic.objectv2.dto.res.ObjectV2Res;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-02T02:10:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class ObjectV2MapperImpl extends ObjectV2Mapper {

    @Override
    public ObjectV2Res toObjectV2Res(ObjectV2Entity objectV2Entity) {
        if ( objectV2Entity == null ) {
            return null;
        }

        ObjectV2Res.ObjectV2ResBuilder<?, ?> objectV2Res = ObjectV2Res.builder();

        objectV2Res.id( objectV2Entity.getId() );
        objectV2Res.createdAt( map( objectV2Entity.getCreatedAt() ) );
        objectV2Res.updatedAt( map( objectV2Entity.getUpdatedAt() ) );
        objectV2Res.categoryId( objectV2Entity.getCategoryId() );
        objectV2Res.name( objectV2Entity.getName() );
        objectV2Res.avatar( objectV2Entity.getAvatar() );
        objectV2Res.address( objectV2Entity.getAddress() );
        objectV2Res.createdBy( objectV2Entity.getCreatedBy() );

        return objectV2Res.build();
    }
}
