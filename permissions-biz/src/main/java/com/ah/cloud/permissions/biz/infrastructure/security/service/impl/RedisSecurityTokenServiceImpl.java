package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityTokenService;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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
public class RedisSecurityTokenServiceImpl implements SecurityTokenService {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 有效期，24小时，单位秒
     */
    private long expires_in = 24 * 60 * 60;

    /**
     * 不足此时间，续期token。可理解为 12 小时内没有请求接口，则 token 失效。
     */
    private long renewal_in = 12 * 60 * 60;

    @Override
    public LocalUser getLocalUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        return this.getLocalUser(getToken(request));
    }

    /**
     * 根据token 获取当前登录信息
     * @param accessToken
     * @return
     */
    @Override
    public LocalUser getLocalUser(String accessToken) {
        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(accessToken)) {
            LocalUser localUser = (LocalUser) redisTemplate.opsForValue().get(getTokenKey(accessToken));
            return localUser;
        }
        return null;
    }

    @Override
    public void deleteLocalUser(String key) {
        redisTemplate.delete(getTokenKey(key));
    }

    @Override
    public AccessToken createToken(LocalUser localUser) {
        log.info("[用户登录] ==> [创建token] 入参:{}", JsonUtils.toJSONString(localUser));
        String accessToken = UUID.randomUUID().toString();
        localUser.setAccessToken(accessToken);
        redisTemplate.opsForValue().set(getTokenKey(accessToken), localUser,  expires_in, TimeUnit.SECONDS);
        return AccessToken.builder()
                .access_token(accessToken)
                .expires_in(expires_in)
                .build();
    }

    @Override
    public String verifyToken(HttpServletRequest request) {
        String token = this.getToken(request);
//        if (StringUtils.isEmpty(token)) {
//            throw new SecurityErrorException(ErrorCodeEnum.TOKEN_EXCEPTION, "token exception", "非法token, 请重新登录");
//        }
        return token;
    }

    @Override
    public void refreshToken(HttpServletRequest request) {
        String accessToken = getToken(request);
        String tokenKey = getTokenKey(accessToken);
        // 获取 当前 token 剩余存活时间
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(tokenKey);
        if(expire > 0 && expire < renewal_in) {
            redisTemplate.expire(tokenKey, expires_in , TimeUnit.SECONDS);
        }
    }

    @Override
    public void refreshToken(String token) {
        String tokenKey = getTokenKey(token);
        // 获取 当前 token 剩余存活时间
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(tokenKey);
        if(expire > 0 && expire < renewal_in) {
            redisTemplate.expire(tokenKey, expires_in , TimeUnit.SECONDS);
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(PermissionsConstants.TOKEN_HEAD);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            // 如果以Bearer开头，则提取。
            if (value.toLowerCase().startsWith(PermissionsConstants.TOKEN_TYPE.toLowerCase())) {
                String authHeaderValue = value.substring(PermissionsConstants.TOKEN_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }


    private String getTokenKey(String accessToken) {
        return PermissionsConstants.TOKEN_KEY_PREFIX + accessToken;
    }
}
