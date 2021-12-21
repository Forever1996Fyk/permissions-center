package com.ah.cloud.permissions.security.service;

import com.ah.cloud.permissions.security.domain.token.AccessToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description: token接口
 * @author: YuKai Fan
 * @create: 2021-12-17 18:26
 **/
public interface TokenService {

    /**
     * 创建token
     * @param o
     * @return
     */
    AccessToken createToken(Object o);

    /**
     * 验证 token 有效期
     * @param accessToken
     */
    void verifyToken(String accessToken);

    /**
     * 请求解析token
     * @param request
     * @return
     */
    String getToken(HttpServletRequest request);
}
