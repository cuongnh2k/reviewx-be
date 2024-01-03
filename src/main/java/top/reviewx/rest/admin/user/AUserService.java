package top.reviewx.rest.admin.user;

import org.springframework.data.domain.Pageable;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.rest.user.user.dto.res.UserRes;

public interface AUserService {

    CommonListResponse<UserRes> getListUser(String name, Boolean isActive, Pageable pageable);

    void deleteUser(String userId);
}
