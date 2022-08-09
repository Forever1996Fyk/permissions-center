package com.ah.cloud.permissions.biz.application.provider.access;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.security.token.InMemoryAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: app端访问
 * @author: YuKai Fan
 * @create: 2022-04-07 17:23
 **/
@Slf4j
@Component
public class InMemeryAccessProvider extends AbstractAccessProvider {

    private final static String LOG_MARK = "InMemeryAccessProvider";

    @Override
    protected boolean doAccess(AuthorityApiDTO authorityApiDTO, Authentication authentication) {
        return needAuthentication(authorityApiDTO, authentication);
    }



    @Override
    public boolean support(Authentication authentication) {
        return InMemoryAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
