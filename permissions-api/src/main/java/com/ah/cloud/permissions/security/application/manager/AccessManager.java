package com.ah.cloud.permissions.security.application.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: permissions-center
 * @description: 认证管理器
 * @author: YuKai Fan
 * @create: 2021-12-17 15:35
 **/
@Slf4j
@Component
public class AccessManager {

    public void access(HttpServletRequest request, Authentication authentication) {

    }
}
