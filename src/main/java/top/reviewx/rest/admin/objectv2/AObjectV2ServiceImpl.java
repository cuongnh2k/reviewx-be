package top.reviewx.rest.admin.objectv2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.entities.object2.ObjectV2Entity;
import top.reviewx.rest.admin.objectv1.AObjectV2Repository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AObjectV2ServiceImpl implements AObjectV2Service {
    private final AObjectV2Repository aObjectV2Repository;

    @Override
    public String deleteObjectV2(String id) {
        ObjectV2Entity objectV2Entity = aObjectV2Repository.findByIdAndIsDeleteFalse(id);
        if (objectV2Entity == null) {
            throw new BusinessLogicException();
        }
        objectV2Entity.setIsDelete(true);
        objectV2Entity.setUpdatedAt(LocalDateTime.now());
        aObjectV2Repository.save(objectV2Entity);
        return id;
    }
}