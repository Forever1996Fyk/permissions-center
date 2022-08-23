package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.domain.user.BaseUserInfo;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-04 17:02
 **/
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录用户授权信息
     * @return
     */
    public static LocalUser getLocalUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.isNull(authentication)) {
                throw new BizException(ErrorCodeEnum.CURRENT_USER_IS_NOT_EXIST);
            }
            if (authentication instanceof AnonymousAuthenticationToken) {
                throw new BizException(ErrorCodeEnum.ANONYMOUS_USER_HAS_NO_INFO);
            }
            return (LocalUser) authentication.getPrincipal();
        } catch (BizException e) {
            log.error("SecurityUtils[getLocalUser] 获取当前登录信息异常, e={}", Throwables.getStackTraceAsString(e));
            throw e;
        }
    }

    /**
     * 获取当前登录用户的基础信息
     * @return
     */
    public static BaseUserInfo getBaseUserInfo() {
        return getLocalUser().getUserInfo();
    }

    /**
     * 获取当前登录人名称
     *
     * @return
     */
    public static String getUserNameBySession() {
        try {
            BaseUserInfo baseUserInfo = getBaseUserInfo();
            return StringUtils.isBlank(baseUserInfo.getName()) ? PermissionsConstants.OPERATOR_SYSTEM : baseUserInfo.getName();
        } catch (Throwable throwable) {
            return PermissionsConstants.OPERATOR_SYSTEM;
        }
    }

    /**
     * 获取当前登录人id
     *
     * @return
     */
    public static Long getUserIdBySession() {
        try {
            BaseUserInfo baseUserInfo = getBaseUserInfo();
            return baseUserInfo.getUserId();
        } catch (Throwable throwable) {
            log.error("SecurityUtils[getUserIdBySession] get userId from cache error, reason is {}", Throwables.getStackTraceAsString(throwable));
            return PermissionsConstants.ZERO;
        }
    }

    /**
     * 获取当前登录人部门编码
     *
     * @return
     */
    public static String getDeptCodeBySession() {
        try {
            BaseUserInfo baseUserInfo = getBaseUserInfo();
            return baseUserInfo.getDeptCode();
        } catch (Throwable throwable) {
            return PermissionsConstants.ZERO_STR;
        }
    }
}
