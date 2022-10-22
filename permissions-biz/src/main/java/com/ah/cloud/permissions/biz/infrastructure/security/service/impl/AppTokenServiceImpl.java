package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.AppUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AbstractTokenService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 16:00
 **/
public class AppTokenServiceImpl extends AbstractTokenService<AccessToken, AppUser> {

    private final static String LOG_MARK = "AppTokenServiceImpl";

    /**
     * 有效期，24小时，单位秒
     */
    private final static long EXPIRES_IN = 24 * 60 * 60;

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected AccessToken buildToken(String token) {
        return AccessToken.builder()
                .accessToken(token)
                .expiresIn(EXPIRES_IN)
                .build();
    }
}
