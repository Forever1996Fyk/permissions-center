package com.ah.cloud.permissions.security.application.manager;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.security.config.ApiProperties;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
import com.ah.cloud.permissions.security.exception.ApiAuthorityErrorException;
import com.ah.cloud.permissions.security.loader.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 认证管理器
 * @author: YuKai Fan
 * @create: 2021-12-17 15:35
 **/
@Slf4j
@Component
public class AccessManager {
    @Autowired
    private ApiProperties apiProperties;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 路径匹配器
     */
    private static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * Spring Security 权限校验表达式方法
     * @param request
     * @param authentication
     * @return
     */
    public boolean access(HttpServletRequest request, Authentication authentication) {
        /*
        获取当前请求uri
         */
        String uri = request.getRequestURI();

        /*
        判断当前uri是否可放行
         */
        if (checkUriIsPermit(uri)) {
            return Boolean.TRUE;
        }

        /*
        根据uri匹配对应的apiCode
         */
        Map<String, AuthorityApiDTO> apiCodeMap = resourceLoader.getUriAndApiCodeMap();
        AuthorityApiDTO authorityApiDTO = apiCodeMap.get(uri);

        /*
        如果没有匹配到apiCode则直接返回false
         */
        if (Objects.isNull(authorityApiDTO)) {
            return Boolean.FALSE;
        }

        /*
        校验当前api属性
         */
        checkApi(authorityApiDTO);

        /*
        获取当前用户信息的apiCode集合
         */
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.warn("AccessManager[access] ==> 当前认证信息为匿名认证 ==> Authentication is AnonymousAuthenticationToken");
            return Boolean.FALSE;
        }

        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = localUser.getAuthorities();
        /*
        判断当前用户是否存在该apiCode
         */
        if (!checkUserHasAuthorities(authorities, authorityApiDTO.getApiCode())) {
            log.warn("AccessManager[access] ==> 当前用户无权限访问 ==> 当前用户信息localUser:{}, 当前uri:{}, 当前api属性authorityApiDTO:{}",
                    JsonUtils.toJSONString(localUser),
                    uri,
                    JsonUtils.toJSONString(authorityApiDTO));
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 校验uri是否可放行
     * @param uri
     * @return
     */
    private boolean checkUriIsPermit(String uri) {
        return apiProperties.getPermitAll().stream()
                .filter(item -> PATH_MATCHER.match(item, uri))
                .findAny()
                .isPresent();
    }

    /**
     * 验证api状态
     * @param authorityApiDTO
     */
    private void checkApi(AuthorityApiDTO authorityApiDTO) {
        if (Objects.equals(authorityApiDTO.getApiStatusEnum(), ApiStatusEnum.DISABLED)) {
            throw new ApiAuthorityErrorException(ErrorCodeEnum.AUTHORITY_API_DISABLED,
                    "api disabled.",
                    "当前接口被禁用");
        }

        if (!authorityApiDTO.getOpen()) {
            throw new ApiAuthorityErrorException(ErrorCodeEnum.AUTHORITY_API_NOT_OPEN,
                    "api not open.",
                    "当前接口未公开");
        }

    }

    /**
     * 校验用户是否存在权限
     * @param authorities
     * @param apiCode
     * @return
     */
    private boolean checkUserHasAuthorities(Collection<? extends GrantedAuthority> authorities, String apiCode) {
        return authorities.stream()
                .filter(item -> StringUtils.equals(item.getAuthority(), apiCode))
                .findFirst()
                .isPresent();
    }
}
