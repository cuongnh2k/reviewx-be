package top.reviewx.rest.admin.objectv1;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.core.enums.ObjectV1StatusEnum;
import top.reviewx.rest.admin.objectv1.dto.req.UpdateObjectV1AdminReq;
import top.reviewx.rest.user.objectv1.dto.res.ObjectV1Res;

public interface AObjectV1Service {
    CommonListResponse<ObjectV1Res> getListObjectV1Admin(String categoryId, String objectId, String name, ObjectV1StatusEnum status, Pageable pageable);

    ObjectV1Res updateObjectV1Admin(String id, UpdateObjectV1AdminReq req);

    ObjectV1Res getOneObjectV1Admin(String id);
}
