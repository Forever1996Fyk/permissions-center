package com.ah.cloud.permissions.biz.infrastructure.security.service;

import com.ah.cloud.permissions.enums.RequestSourceEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 16:10
 **/
public interface AuthenticationTokenFilterService {

    /**
     * 认证token过滤流程
     *
     * @param request
     * @param response
     */
    void process(HttpServletRequest request, HttpServletResponse response);

    /**
     * 清楚认证流程
     * @param request
     * @param response
     */
    void cleanAuthenticationProcess(HttpServletRequest request, HttpServletResponse response);

    /**
     * 请求来源
     * @return
     */
    RequestSourceEnum getRequestSourceEnum();
}
