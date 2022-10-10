package com.ah.cloud.permissions.biz.infrastructure.security.handler;

import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.infrastructure.constant.TokenConstants;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityUserService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/5 09:54
 **/
public abstract class AbstractLogoutSuccessHandler<T extends Token, U extends UserDetails> implements LogoutSuccessHandler {
    private final TokenService<T, U> tokenService;
    private final SecurityUserService<U> securityUserService;

    protected AbstractLogoutSuccessHandler(TokenService<T, U> tokenService, SecurityUserService<U> securityUserService) {
        this.tokenService = tokenService;
        this.securityUserService = securityUserService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        beforeLogout(request, response, authentication);
        /*
        退出登录步骤，最终肯定需要让token失效，并清除当前登录人信息
         */
        Map<String, String> tokenMap = tokenService.getToken(request);
        if (CollectionUtils.isEmpty(tokenMap)) {
            return;
        }
        String token = tokenMap.get(TokenConstants.TOKEN_MAP_KEY);
        securityUserService.deleteUserDetails(token);
        afterLogout(request, response, authentication);
    }

    /**
     * 登出前处理
     * @param request
     * @param response
     * @param authentication
     */
    protected void beforeLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {}

    /**
     * 登出后处理
     * @param request
     * @param response
     * @param authentication
     */
    protected void afterLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {}
}
