package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.WebSecurityTokenService;
import lombok.extern.slf4j.Slf4j;
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
    private WebSecurityTokenService webSecurityTokenService;

    /**
     * 根据HttpServletRequest 从redis获取当前用户信息
     * @param request
     * @return
     */
    public LocalUser getLocalUserByRequest(HttpServletRequest request) {
        /*
        验证token是否有效, 并返回token
         */
        String token = webSecurityTokenService.verifyToken(request);
        /*
        根据token获取当前登录用户信息
         */
        return webSecurityTokenService.getLocalUser(token);
    }

    /**
     * 刷新token过期时间
     * @param token
     */
    public void refreshToken(String token) {
        webSecurityTokenService.refreshToken(token);
    }

    /**
     * 清除token
     * @param token
     */
    public void clearToken(String token) {
        webSecurityTokenService.deleteLocalUser(token);
    }

}
