package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.AppUser;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AbstractAuthenticationTokenFilterService;
import com.ah.cloud.permissions.enums.RequestSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 16:14
 **/
@Component
public class AppAuthenticationTokenFilterServiceImpl extends AbstractAuthenticationTokenFilterService<AccessToken, AppUser> {

    @Autowired
    protected AppAuthenticationTokenFilterServiceImpl(AppSecurityUserServiceImpl securityUserService) {
        super(new AppTokenServiceImpl(), securityUserService);
    }

    @Override
    public void cleanAuthenticationProcess(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public RequestSourceEnum getRequestSourceEnum() {
        return RequestSourceEnum.MT;
    }
}
