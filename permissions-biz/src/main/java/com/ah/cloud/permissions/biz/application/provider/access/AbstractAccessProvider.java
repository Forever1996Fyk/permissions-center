package com.ah.cloud.permissions.biz.application.provider.access;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.exception.ApiAuthorityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.security.loader.ResourceLoader;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-07 17:00
 **/
@Slf4j
@Component
public abstract class AbstractAccessProvider implements AccessProvider {
    @Resource
    protected ResourceLoader resourceLoader;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        /*
        获取当前请求uri
         */
        String uri = request.getRequestURI();

        /*
        根据uri匹配对应的apiCode
         */
        AuthorityApiDTO authorityApiDTO = resourceLoader.getCacheResourceByUri(uri);
        /*
        如果没有匹配到apiCode则直接返回false
         */
        if (Objects.isNull(authorityApiDTO)) {
            log.error("{}[access] get apiCodeKey by uri is empty, uri is {}", getLogMark(), uri);
            return Boolean.FALSE;
        }
        /*
        校验当前api属性
         */
        checkApi(authorityApiDTO);
        log.info("{}[access] current authentication token doAccess", getLogMark());
        return doAccess(authorityApiDTO, authentication);
    }

    /**
     * 处理访问逻辑
     * @param authorityApiDTO
     * @param authentication
     * @return
     */
    protected abstract boolean doAccess(AuthorityApiDTO authorityApiDTO, Authentication authentication);

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 验证api状态
     *
     * @param authorityApiDTO
     */
    private void checkApi(AuthorityApiDTO authorityApiDTO) {
        if (Objects.equals(authorityApiDTO.getApiStatusEnum(), ApiStatusEnum.DISABLED)) {
            throw new ApiAuthorityErrorException(ErrorCodeEnum.AUTHORITY_API_DISABLED);
        }

        if (!authorityApiDTO.getOpen()) {
            throw new ApiAuthorityErrorException(ErrorCodeEnum.AUTHORITY_API_NOT_OPEN);
        }
    }

    protected boolean needAuthentication(AuthorityApiDTO authorityApiDTO, Authentication authentication) {
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = localUser.getAuthorities();
        /*
        判断当前用户是否存在该apiCode
         */
        if (!checkUserHasAuthorities(authorities, authorityApiDTO.getApiCode())) {
            log.warn("AccessManager[access] currentUser has no permissions access localUser={}, authorityApiDTO={}",
                    JsonUtils.toJSONString(localUser),
                    JsonUtils.toJSONString(authorityApiDTO)
            );
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 校验用户是否存在权限
     *
     * @param authorities
     * @param apiCode
     * @return
     */
    private boolean checkUserHasAuthorities(Collection<? extends GrantedAuthority> authorities, String apiCode) {
        return authorities.stream()
                .anyMatch(item -> StringUtils.equals(item.getAuthority(), apiCode));
    }
}
