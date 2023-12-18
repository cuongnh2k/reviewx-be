package top.reviewx.rest.basic.auth;

import top.reviewx.rest.basic.auth.dto.req.ActiveUserReq;
import top.reviewx.rest.basic.auth.dto.req.ResetPasswordReq;
import top.reviewx.rest.basic.auth.dto.req.SignInReq;
import top.reviewx.rest.basic.auth.dto.req.SignUpReq;
import top.reviewx.rest.basic.auth.dto.res.SignInRes;
import top.reviewx.rest.user.user.dto.res.UserRes;

public interface BUserService {

    UserRes signUp(SignUpReq req);

    SignInRes signIn(SignInReq req);

    UserRes active(ActiveUserReq req);

    UserRes resetPassword(ResetPasswordReq req);
}
