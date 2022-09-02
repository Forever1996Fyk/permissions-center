package com.ah.cloud.permissions.biz.application.provider.access;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.security.token.AppAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: app端访问
 * @author: YuKai Fan
 * @create: 2022-04-07 17:23
 **/
@Slf4j
@Component
public class AppAccessProvider extends AbstractAccessProvider {

    private final static String LOG_MARK = "AppAccessProvider";

    /**
     * app 端访问 只要登录, 直接全部放行权限即可
     *
     * @param request
     * @param authentication
     * @return
     */
    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken || Objects.isNull(authentication.getPrincipal())) {
            log.error("AppAccessProvider[access] app user not login, uri is {}", request.getRequestURI());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

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
