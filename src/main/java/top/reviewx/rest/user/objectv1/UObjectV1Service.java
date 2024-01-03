package top.reviewx.rest.user.objectv1;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.rest.user.objectv1.dto.req.CreateObjectV1Req;
import top.reviewx.rest.user.objectv1.dto.req.UpdateObjectV1UserReq;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;

public interface UObjectV1Service {
    ObjectV1Res createObjectV1(CreateObjectV1Req req);

    CommonListResponse<ObjectV1Res> getListObjectV1User(String categoryId, String objectId, String name, ObjectV1StatusEnum status, Pageable pageable);

    ObjectV1Res updateObjectV1User(String id, UpdateObjectV1UserReq req);

    void deleteObjectV1User(String id);

    ObjectV1Res getOneObjectV1User(String id);
}
