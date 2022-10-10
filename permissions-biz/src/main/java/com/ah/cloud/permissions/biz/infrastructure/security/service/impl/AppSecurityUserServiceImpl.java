package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.user.AppUser;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.SecurityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityUserService;
import com.ah.cloud.permissions.biz.infrastructure.security.token.AppAuthenticationToken;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 17:06
 **/
@Slf4j
@Component
public class AppSecurityUserServiceImpl implements SecurityUserService<AppUser> {
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    /**
     * 有效期，24小时，单位秒
     */
    private final static long EXPIRES_IN = 24 * 60 * 60;

    /**
     * 不足此时间，续期token。可理解为 12 小时内没有请求接口，则 token 失效。
     */
    private final static long RENEWAL_IN = 12 * 60 * 60;

    @Override
    public void saveUserDetails(AppUser appUser) {
        redisCacheHandleStrategy.setCacheObjectByExpire(getTokenKey(appUser.getAccessToken()), appUser, EXPIRES_IN, TimeUnit.SECONDS);
    }

    @Override
    public AppUser getUserDetails(String token) {
        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(token)) {
            return redisCacheHandleStrategy.getCacheObject(getTokenKey(token));
        }
        return null;
    }

    private String getTokenKey(String accessToken) {
        return PermissionsConstants.APP_TOKEN_KEY_PREFIX + accessToken;
    }

    @Override
    public void deleteUserDetails(String key) {
        redisCacheHandleStrategy.deleteObject(getTokenKey(key));
    }

    @Override
    public void refreshExpire(String token) {
        String tokenKey = getTokenKey(token);
        // 获取 当前 token 剩余存活时间
        long expire = redisCacheHandleStrategy.getExpireTimeByKey(tokenKey);
        if (expire > 0 && expire < RENEWAL_IN) {
            redisCacheHandleStrategy.expire(tokenKey, EXPIRES_IN, TimeUnit.SECONDS);
        }
    }

    @Override
    public void authenticationUserDetails(AppUser appUser) {
        // 判断当前登录人信息是否可用
        if (!appUser.isEnabled()) {
            throw new SecurityErrorException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_DISABLED);
        }
        this.refreshExpire(appUser.getAccessToken());
        // 判断当前上下文是否存储认证信息
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) ) {
            AppAuthenticationToken authenticationToken = new AppAuthenticationToken(appUser, null, appUser.getAuthorities());
            /*
            设置到上下文 方便获取当前登录人信息 可自定义策略SecurityContextHolderStrategy
            通过spring.security.strategy 设置对应的策略类全限定名(类路径)即可
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
