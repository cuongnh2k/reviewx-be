package top.reviewx.rest.basic.objectv2;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.rest.basic.objectv2.dto.res.ObjectV2Res;

public interface BObjectV2Service {

    CommonListResponse<ObjectV2Res> getListObjectV2(String categoryId, String name, Pageable pageable);

    ObjectV2Res getOneObjectV2(String id);
}
