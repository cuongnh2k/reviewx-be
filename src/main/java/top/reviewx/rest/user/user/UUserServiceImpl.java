package top.reviewx.rest.user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.reviewx.core.common.CommonAuthContext;
import top.reviewx.core.exception.BusinessLogicException;
import top.reviewx.mapper.UserMapper;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.rest.user.user.dto.req.UpdateUserReq;
import top.reviewx.rest.user.user.dto.res.UserRes;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UUserServiceImpl implements UUserService {
    private final UUserRepository uUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final CommonAuthContext authContext;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserRes getUser() {
        UserEntity userEntity = uUserRepository.findById(authContext.getId()).orElse(null);
        return userMapper.toUserRes(userEntity);
    }

    @Override
    public UserRes updateUser(UpdateUserReq req) {
        UserEntity userEntity = uUserRepository.findById(authContext.getId()).orElse(null);
        assert userEntity != null;
        if (StringUtils.hasText(req.getNewPassword())) {
            try {
                daoAuthenticationProvider.authenticate(
                        new UsernamePasswordAuthenticationToken(userEntity.getLocal().getEmail(), req.getOldPassword()));
            } catch (BadCredentialsException e) {
                throw new BusinessLogicException();
            }
            userEntity.getLocal().setPassword(passwordEncoder.encode(req.getNewPassword()));
            userEntity.setUpdatedAt(LocalDateTime.now());
        }
        if (StringUtils.hasText(req.getName())) {
            userEntity.getLocal().setName(req.getName());
            userEntity.setUpdatedAt(LocalDateTime.now());
        }
        if (StringUtils.hasText(req.getAvatar())) {
            userEntity.getLocal().setAvatar(req.getAvatar());
            userEntity.setUpdatedAt(LocalDateTime.now());
        }
        return userMapper.toUserRes(uUserRepository.save(userEntity));
    }
}