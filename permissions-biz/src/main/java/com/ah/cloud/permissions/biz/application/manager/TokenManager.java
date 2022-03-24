package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource
    private SecurityTokenService securityTokenService;

    /**
     * 根据HttpServletRequest 从redis获取当前用户信息
     * @param request
     * @return
     */
    public LocalUser getLocalUserByRequest(HttpServletRequest request) {
        /*
        验证token是否有效, 并返回token
         */
        String token = securityTokenService.verifyToken(request);
        /*
        根据token获取当前登录用户信息
         */
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

    /**
     * 清除token
     * @param token
     */
    public void clearToken(String token) {
        securityTokenService.deleteLocalUser(token);
    }

}
