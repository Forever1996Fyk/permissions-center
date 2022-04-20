package com.ah.cloud.permissions.biz.application.provider.access;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.security.token.AppAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: app端访问
 * @author: YuKai Fan
 * @create: 2022-04-07 17:23
 **/
@Component
public class AppAccessProvider extends AbstractAccessProvider {

    private final static String LOG_MARK = "AppAccessProvider";

    @Override
    protected boolean doAccess(AuthorityApiDTO authorityApiDTO, Authentication authentication) {
        return Boolean.TRUE;
    }


    @Override
    public boolean support(Authentication authentication) {
        return AppAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
