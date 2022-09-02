package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.application.manager.AccessManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.security.service.WebSecurityTokenService;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description: 认证token 用户信息接口
 * @author: YuKai Fan
 * @create: 2021-12-21 18:05
 **/
@Slf4j
@Component
public class RedisWebSecurityTokenServiceImpl implements WebSecurityTokenService {
    @Resource
    private AccessManager accessManager;
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
    public LocalUser getLocalUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        return this.getLocalUser(getToken(request));
    }

    /**
     * 根据token 获取当前登录信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public LocalUser getLocalUser(String accessToken) {
        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(accessToken)) {
            String userId = redisCacheHandleStrategy.getCacheObject(getTokenKey(accessToken));
            if (StringUtils.isEmpty(userId)) {
                return null;
            }
            return (LocalUser) accessManager.getUserDetailsFromRedis(userId);
        }
        return null;
    }

    @Override
    public void deleteLocalUser(String key) {
        redisCacheHandleStrategy.deleteObject(getTokenKey(key));
    }

    @Override
    public AccessToken createToken(LocalUser localUser) {
        log.info("RedisWebSecurityTokenServiceImpl[createToken] createToken localUser={}", JsonUtils.toJsonString(localUser));
        String accessToken = UUID.randomUUID().toString();
        /*
        设置token缓存
        key: token
        value: userId
         */
        redisCacheHandleStrategy.setCacheObjectByExpire(getTokenKey(accessToken), String.valueOf(localUser.getUserInfo().getUserId()), EXPIRES_IN, TimeUnit.SECONDS);

        accessManager.setUserAuthRedisCache(localUser.getUserInfo().getUserId(), localUser);
        return AccessToken.builder()
                .accessToken(accessToken)
                .expiresIn(EXPIRES_IN)
                .build();
    }

    @Override
    public void verifyToken(String token) {
    }

    @Override
    public void refreshToken(String token) {
        String tokenKey = getTokenKey(token);
        // 获取 当前 token 剩余存活时间
        long expire = redisCacheHandleStrategy.getExpireTimeByKey(tokenKey);
        if (expire > 0 && expire < RENEWAL_IN) {
            redisCacheHandleStrategy.expire(tokenKey, EXPIRES_IN, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(PermissionsConstants.TOKEN_HEAD);
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        // 如果以Bearer开头，则提取。
        if (header.toLowerCase().startsWith(PermissionsConstants.TOKEN_TYPE.toLowerCase())) {
            String authHeaderValue = header.substring(PermissionsConstants.TOKEN_TYPE.length()).trim();
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return authHeaderValue;
        }
        return null;
    }


    private String getTokenKey(String accessToken) {
        return PermissionsConstants.TOKEN_KEY_PREFIX + accessToken;
    }
}
