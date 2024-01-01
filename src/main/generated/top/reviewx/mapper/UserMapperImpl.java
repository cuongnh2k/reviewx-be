package top.reviewx.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.core.enums.RoleEnum;
import top.reviewx.entities.user.LocalEntity;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.rest.user.user.dto.res.LocalRes;
import top.reviewx.rest.user.user.dto.res.UserRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-01T18:14:34+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserRes toUserRes(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserRes.UserResBuilder<?, ?> userRes = UserRes.builder();

        userRes.id( userEntity.getId() );
        userRes.createdAt( map( userEntity.getCreatedAt() ) );
        userRes.updatedAt( map( userEntity.getUpdatedAt() ) );
        List<RoleEnum> list = userEntity.getRoles();
        if ( list != null ) {
            userRes.roles( new ArrayList<RoleEnum>( list ) );
        }
        userRes.local( localEntityToLocalRes( userEntity.getLocal() ) );
        userRes.facebook( userEntity.getFacebook() );
        userRes.google( userEntity.getGoogle() );

        return userRes.build();
    }

    protected LocalRes localEntityToLocalRes(LocalEntity localEntity) {
        if ( localEntity == null ) {
            return null;
        }

        LocalRes.LocalResBuilder localRes = LocalRes.builder();

        localRes.email( localEntity.getEmail() );
        localRes.name( localEntity.getName() );
        localRes.avatar( localEntity.getAvatar() );
        localRes.isActive( localEntity.getIsActive() );

        return localRes.build();
    }
}
