package top.reviewx.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import top.reviewx.entities.object1.NoteEntity;
import top.reviewx.entities.object1.ObjectV1Entity;
import top.reviewx.rest.user.objectv1.dto.res.NoteRes;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T01:37:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class ObjectV1MapperImpl extends ObjectV1Mapper {

    @Override
    public ObjectV1Res toObjectV1Res(ObjectV1Entity objectV1Entity) {
        if ( objectV1Entity == null ) {
            return null;
        }

        ObjectV1Res.ObjectV1ResBuilder<?, ?> objectV1Res = ObjectV1Res.builder();

        objectV1Res.id( objectV1Entity.getId() );
        objectV1Res.createdAt( map( objectV1Entity.getCreatedAt() ) );
        objectV1Res.updatedAt( map( objectV1Entity.getUpdatedAt() ) );
        objectV1Res.objectId( objectV1Entity.getObjectId() );
        objectV1Res.categoryId( objectV1Entity.getCategoryId() );
        objectV1Res.name( objectV1Entity.getName() );
        objectV1Res.avatar( objectV1Entity.getAvatar() );
        objectV1Res.address( objectV1Entity.getAddress() );
        objectV1Res.status( objectV1Entity.getStatus() );
        objectV1Res.notes( noteEntityListToNoteResList( objectV1Entity.getNotes() ) );
        objectV1Res.createdBy( objectV1Entity.getCreatedBy() );

        return objectV1Res.build();
    }

    protected NoteRes noteEntityToNoteRes(NoteEntity noteEntity) {
        if ( noteEntity == null ) {
            return null;
        }

        NoteRes.NoteResBuilder<?, ?> noteRes = NoteRes.builder();

        noteRes.id( noteEntity.getId() );
        noteRes.name( noteEntity.getName() );
        noteRes.avatar( noteEntity.getAvatar() );
        noteRes.content( noteEntity.getContent() );
        noteRes.createdAt( noteEntity.getCreatedAt() );
        noteRes.updatedAt( noteEntity.getUpdatedAt() );

        return noteRes.build();
    }

    protected List<NoteRes> noteEntityListToNoteResList(List<NoteEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<NoteRes> list1 = new ArrayList<NoteRes>( list.size() );
        for ( NoteEntity noteEntity : list ) {
            list1.add( noteEntityToNoteRes( noteEntity ) );
        }

        return list1;
    }
}
