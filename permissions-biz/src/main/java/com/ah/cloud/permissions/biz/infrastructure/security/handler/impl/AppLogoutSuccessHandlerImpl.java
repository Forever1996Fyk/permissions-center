package com.ah.cloud.permissions.biz.infrastructure.security.handler.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.AppUser;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AbstractLogoutSuccessHandler;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityUserService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.AppSecurityUserServiceImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.AppTokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/6 10:21
 **/
@Slf4j
@Component
public class AppLogoutSuccessHandlerImpl extends AbstractLogoutSuccessHandler<AccessToken, AppUser> {

    @Autowired
    protected AppLogoutSuccessHandlerImpl(AppSecurityUserServiceImpl appSecurityUserService) {
        super(new AppTokenServiceImpl(), appSecurityUserService);
    }
}
