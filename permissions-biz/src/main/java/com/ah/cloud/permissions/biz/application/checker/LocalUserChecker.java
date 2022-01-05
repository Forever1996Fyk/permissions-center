package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.UserAccountErrorException;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-23 16:13
 **/
@Component
public class LocalUserChecker {

    /**
     * 校验用户权限信息
     * @param userAuthorityDTO
     */
    public void checkUserAuthority(UserAuthorityDTO userAuthorityDTO) {
        if (Objects.isNull(userAuthorityDTO)) {
            throw new UserAccountErrorException(ErrorCodeEnum.ACCOUNT_ERROR);
        }
        checkUserStatus(userAuthorityDTO.getUserStatusEnum());
        checkUserAuthorities(userAuthorityDTO.getAuthorities());
    }

    /**
     * 校验用户状态
     * @param userStatusEnum
     */
    public void checkUserStatus(UserStatusEnum userStatusEnum) {
        if (Objects.equals(userStatusEnum, UserStatusEnum.DISABLED)) {
            throw new UserAccountErrorException(ErrorCodeEnum.ACCOUNT_DISABLED);
        }

        if (Objects.equals(userStatusEnum, UserStatusEnum.LOG_OFF)) {
            throw new UserAccountErrorException(ErrorCodeEnum.ACCOUNT_LOG_OFF);
        }
    }

    /**
     * 校验用户权限
     * @param authorities
     */
    public void checkUserAuthorities(Set<String> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            throw new UserAccountErrorException(ErrorCodeEnum.PERMISSION_DENY);
        }
    }
}
