package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.constant.TokenConstants;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AbstractAuthenticationTokenFilterService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AuthenticationTokenFilterService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityUserService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import com.ah.cloud.permissions.enums.RequestSourceEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 16:14
 **/
@Component
public class WebAuthenticationTokenFilterServiceImpl extends AbstractAuthenticationTokenFilterService<AccessToken, LocalUser> {

    @Autowired
    protected WebAuthenticationTokenFilterServiceImpl(WebSecurityUserServiceImpl securityUserService) {
        super(new WebTokenServiceImpl(), securityUserService);
    }

    @Override
    public void cleanAuthenticationProcess(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public RequestSourceEnum getRequestSourceEnum() {
        return RequestSourceEnum.WEB;
    }
}
