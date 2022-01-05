package com.ah.cloud.permissions.biz.infrastructure.security.service;


import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;

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
     * @param localUser
     * @return
     */
    AccessToken createToken(LocalUser localUser);

    /**
     * 验证 token 合法
     * @param request
     */
    String verifyToken(HttpServletRequest request);

    /**
     * 刷新token有效期
     * @param token
     */
    void refreshToken(String token);

    /**
     * 刷新token有效期
     * @param request
     */
    void refreshToken(HttpServletRequest request);

    /**
     * 请求解析token
     * @param request
     * @return
     */
    String getToken(HttpServletRequest request);
}
