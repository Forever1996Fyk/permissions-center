package com.ah.cloud.permissions.security.application.manager;

import com.ah.cloud.permissions.security.domain.user.LocalUser;
import com.ah.cloud.permissions.security.service.SecurityTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description: token 管理器
 * @author: YuKai Fan
 * @create: 2021-12-22 15:35
 **/
@Slf4j
@Component
public class TokenManager {
    @Autowired
    private SecurityTokenService securityTokenService;

    /**
     * 根据HttpServletRequest 从redis获取当前用户信息
     * @param request
     * @return
     */
    public LocalUser getLocalUserByRequest(HttpServletRequest request) {
         /*
          从 request 中提取到token，获取当前登录用户
         */
        String token = securityTokenService.verifyToken(request);
        LocalUser localUser = securityTokenService.getLocalUser(token);
        return localUser;
    }

    /**
     * 刷新token过期时间
     * @param token
     */
    public void refreshToken(String token) {
        securityTokenService.refreshToken(token);
    }

}
