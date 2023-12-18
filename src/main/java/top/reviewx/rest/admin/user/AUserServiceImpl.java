package top.reviewx.rest.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.reviewx.core.common.CommonListResponse;
import top.reviewx.mapper.UserMapper;
import top.reviewx.entities.user.UserEntity;
import top.reviewx.rest.user.user.dto.res.UserRes;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AUserServiceImpl implements AUserService {
    private final AUserRepository aUserRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<UserRes> getListUser(String name, Boolean isActive, Pageable pageable) {
        Page<UserEntity> userEntityPage;
        if (StringUtils.hasText(name) && isActive != null) {
            userEntityPage = aUserRepository.findByLocal_NameAndLocal_IsActive(name, isActive, pageable);
        } else if (StringUtils.hasText(name)) {
            userEntityPage = aUserRepository.findByLocal_Name(name, pageable);
        } else if ((isActive != null)) {
            userEntityPage = aUserRepository.findByLocal_IsActive(isActive, pageable);
        } else userEntityPage = aUserRepository.findAll(pageable);

        return CommonListResponse.<UserRes>builder()
                .content(userEntityPage.getContent().stream()
                        .map(userMapper::toUserRes)
                        .collect(Collectors.toList()))
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalPages(userEntityPage.getTotalPages())
                .totalElements(userEntityPage.getTotalElements())
                .build();
    }
}