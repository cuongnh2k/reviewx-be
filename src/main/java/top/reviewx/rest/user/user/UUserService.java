package top.reviewx.rest.user.user;

import top.reviewx.rest.user.user.dto.req.UpdateUserReq;
import top.reviewx.rest.user.user.dto.res.UserRes;

public interface UUserService {

    UserRes getUser();

    UserRes updateUser(UpdateUserReq req);
}
