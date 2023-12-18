package top.reviewx.rest.basic.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.enums.RoleEnum;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.core.utils.SendEmailUtil;
import top.reviewx.entities.user.LocalEntity;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.mapper.UserMapper;
import top.reviewx.rest.basic.auth.dto.req.ActiveUserReq;
import top.reviewx.rest.basic.auth.dto.req.ResetPasswordReq;
import top.reviewx.rest.basic.auth.dto.req.SignInReq;
import top.reviewx.rest.basic.auth.dto.req.SignUpReq;
import top.reviewx.rest.basic.auth.dto.res.SignInRes;
import top.reviewx.rest.user.user.dto.res.UserRes;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BUserServiceImpl implements BUserService {
    private final BUserRepository bUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailUtil sendEmailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final HttpServletRequest request;
    private final CommonAuthContext authContext;
    private final UserMapper userMapper;
    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.jwt.access-token-age}")
    private Long ACCESS_TOKEN_AGE;

    @Override
    public UserRes signUp(SignUpReq req) {
        UserEntity userEntity = bUserRepository.findByLocal_Email(req.getEmail());
        if (userEntity != null) {
            throw new BusinessLogicException(-1);
        }
        String verifyToken = RandomStringUtils.random(4, false, true);
        userEntity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .roles(Collections.singletonList(RoleEnum.USER))
                .local(LocalEntity.builder()
                        .email(req.getEmail())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .name(req.getName())
                        .isActive(false)
                        .verifyToken(verifyToken)
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        sendEmailUtil.sendSimpleMessage(req.getEmail(), "Mã kích hoạt tài khoản", verifyToken);
        return userMapper.toUserRes(bUserRepository.insert(userEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public SignInRes signIn(SignInReq req) {
        try {
            daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BusinessLogicException();
        }
        UserEntity userEntity = bUserRepository.findByLocal_Email(req.getEmail());
        if (!userEntity.getLocal().getIsActive()) {
            throw new BusinessLogicException();
        }
        return new SignInRes(JWT.create()
                .withSubject(userEntity.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes())));
    }

    @Override
    public UserRes active(ActiveUserReq req) {
        UserEntity userEntity = bUserRepository.findByLocal_Email(req.getEmail());
        if (userEntity == null) {
            throw new BusinessLogicException();
        }
        if (!req.getVerifyToken().equals(userEntity.getLocal().getVerifyToken())) {
            throw new BusinessLogicException();
        }
        userEntity.getLocal().setIsActive(true);
        userEntity.setUpdatedAt(LocalDateTime.now());
        return userMapper.toUserRes(bUserRepository.save(userEntity));
    }

    @Override
    public UserRes resetPassword(ResetPasswordReq req) {
        UserEntity userEntity = bUserRepository.findByLocal_Email(req.getEmail());
        if (userEntity == null) {
            throw new BusinessLogicException();
        }
        String newPassword = RandomStringUtils.random(5, "1234567890")
                + RandomStringUtils.random(1, "qwertyuiop")
                + RandomStringUtils.random(1, "ASDFGHJKLZ")
                + RandomStringUtils.random(1, "!@#$%^&*()");
        userEntity.getLocal().setPassword(passwordEncoder.encode(newPassword));
        userEntity.setUpdatedAt(LocalDateTime.now());
        sendEmailUtil.sendSimpleMessage(userEntity.getLocal().getEmail(), "Mật khẩu mới", newPassword);
        return userMapper.toUserRes(bUserRepository.save(userEntity));
    }
}