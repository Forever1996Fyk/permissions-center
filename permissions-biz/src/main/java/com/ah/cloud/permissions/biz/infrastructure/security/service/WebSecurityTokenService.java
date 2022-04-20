package com.ah.cloud.permissions.biz.infrastructure.security.service;


import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-21 17:20
 **/
public interface WebSecurityTokenService extends TokenService<AccessToken, LocalUser> {

    /**
     * 获取当前Security 用户信息
     * @param request
     * @return
     */
    LocalUser getLocalUser(HttpServletRequest request);

    /**
     * 获取当前Security 用户信息
     * @param token
     * @return
     */
    LocalUser getLocalUser(String token);

    /**
     * 删除当前Security 用户信息
     * @param key
     */
    void deleteLocalUser(String key);
}
