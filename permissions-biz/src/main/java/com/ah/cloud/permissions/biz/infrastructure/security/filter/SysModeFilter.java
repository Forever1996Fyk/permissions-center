package com.ah.cloud.permissions.biz.infrastructure.security.filter;

import com.ah.cloud.permissions.biz.application.manager.AccessManager;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.SecurityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.exception.WriteApiCannotAccessException;
import com.ah.cloud.permissions.biz.infrastructure.security.loader.ResourceLoader;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 系统模式过滤器
 * @author: YuKai Fan
 * @create: 2022-08-23 14:32
 **/
@Slf4j
@Component
public class SysModeFilter extends OncePerRequestFilter {
    /**
     * 系统模式
     *
     * 如果是 visitor 模式，表示游客模式, 则无法执行写操作
     */
    @Value("${system.mode:visitor}")
    private String mode;

    private final static String VISITOR_MODE = "visitor";

    @Resource
    private AccessManager accessManager;
    @Resource
    private ResourceLoader resourceLoader;
    /**
     * 校验失败处理器
     */
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (accessManager.checkUriIsPermit(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        AuthorityApiDTO authorityApiDTO = resourceLoader.getCacheResourceByUri(uri);
        if (Objects.isNull(authorityApiDTO)) {
            log.error("SysModeFilter[doFilterInternal] get AuthorityApiDTO by uri result is empty, uri is {}", uri);
            authenticationFailureHandler.onAuthenticationFailure(request, response, new SecurityErrorException(ErrorCodeEnum.UNKNOWN_PERMISSION));
            return;
        }
        if (StringUtils.equals(mode, VISITOR_MODE) && authorityApiDTO.getReadOrWriteEnum().isWrite()) {
            log.error("SysModeFilter[doFilterInternal] current mode is visitor, cannot access write type api");
            authenticationFailureHandler.onAuthenticationFailure(request, response, new WriteApiCannotAccessException(ErrorCodeEnum.VISITOR_MODE_CANNOT_ACCESS_RESOURCE));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
