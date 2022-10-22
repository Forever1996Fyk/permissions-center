package com.ah.cloud.permissions.biz.infrastructure.security.handler.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AbstractLogoutSuccessHandler;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebSecurityUserServiceImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebTokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/6 10:32
 **/
@Slf4j
@Component
public class WebLogoutSuccessHandlerImpl extends AbstractLogoutSuccessHandler<AccessToken, LocalUser> {

    @Autowired
    protected WebLogoutSuccessHandlerImpl(WebSecurityUserServiceImpl webSecurityUserService) {
        super(new WebTokenServiceImpl(), webSecurityUserService);
    }
}
