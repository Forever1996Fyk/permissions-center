package com.ah.cloud.permissions.biz.infrastructure.security.service;


import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.enums.RequestSourceEnum;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: permissions-center
 * @description: token接口
 * @author: YuKai Fan
 * @create: 2021-12-17 18:26
 **/
public interface TokenService<T extends Token, U extends UserDetails> {

    /**
     * 创建token
     * @param u
     * @return
     */
    T createToken(U u);

    /**
     * 验证 token 合法
     * @param token
     */
    Map<String, String> verifyToken(String token);

    /**
     * 刷新token有效期
     * @param token
     */
    void refreshToken(String token);

    /**
     * 请求解析token
     * @param request
     * @return
     */
    Map<String, String> getToken(HttpServletRequest request);
}
