package com.ah.cloud.permissions.biz.application.provider.access;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-07 16:58
 **/
public interface AccessProvider {

    /**
     * 处理访问权限
     * @param request
     * @param authentication
     * @return
     */
    boolean access(HttpServletRequest request, Authentication authentication);

    /**
     * 是否支持
     * @param authentication
     * @return
     */
    boolean support(Authentication authentication);
}
