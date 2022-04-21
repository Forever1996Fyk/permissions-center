package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.LocalUserHelper;
import com.ah.cloud.permissions.biz.application.manager.login.LoginManager;
import com.ah.cloud.permissions.biz.application.provider.access.AccessProvider;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.config.ApiProperties;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.ApiAuthorityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.security.loader.ResourceLoader;
import com.ah.cloud.permissions.biz.infrastructure.security.token.AppAuthenticationToken;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
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
public class AccessManager implements InitializingBean {
    @Resource
    private LocalUserHelper localUserHelper;
    @Resource
    private UserAuthManager userAuthManager;
    @Resource
    private List<AccessProvider> accessProviderList;
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    /**
     * Spring Security 权限校验表达式方法
     *
     * @param request
     * @param authentication
     * @return
     */
    public boolean access(HttpServletRequest request, Authentication authentication) {
        int currentPosition = 0;
        int size = this.accessProviderList.size();
        for (AccessProvider accessProvider : accessProviderList) {
            if (!accessProvider.support(authentication)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("AccessManager request with %s (%d/%d)",
                        authentication.getClass().getSimpleName(), ++currentPosition, size)));
            }
            return accessProvider.access(request, authentication);
        }
        log.error("AccessManager has no Provider");
        return false;
    }


    /**
     * 校验用户权限是否变更
     * @param localUser
     * @return
     */
    public boolean isUserAuthChanged(LocalUser localUser) {
        return localUser.isAuthorityChanged();
    }

    /**
     * 重置用户权限标志
     * @param localUser
     */
    public void resetUserAuthChangedMark(LocalUser localUser, boolean changed) {
        localUser.setAuthorityChanged(changed);
        setUserAuthRedisCache(localUser.getUserInfo().getUserId(), localUser);
    }

    /**
     * 设置用户信息缓存
     *  key: userId
     *  value: localUser
     * @param userId
     * @param userDetails
     */
    public void setUserAuthRedisCache(Long userId, UserDetails userDetails) {
        redisCacheHandleStrategy.setCacheHashValue(PermissionsConstants.TOKEN_USER_KEY, String.valueOf(userId), userDetails);
    }

    /**
     * 从redis获取当前用户信息
     * @param userId
     * @return
     */
    public UserDetails getUserDetailsFromRedis(String userId) {
        return redisCacheHandleStrategy.getCacheHashByKey(PermissionsConstants.TOKEN_USER_KEY, userId);
    }

    /**
     * 重新构造当前登录人信息, 并放入缓存
     * @param userId
     */
    public void updateUserCache(Long userId) {
        UserAuthorityDTO userAuthorityDTO = userAuthManager.createUserAuthorityByUserId(userId);
        LocalUser localUser = localUserHelper.buildLocalUser(userAuthorityDTO);
        this.setUserAuthRedisCache(userId, localUser);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(accessProviderList)) {
            throw new RuntimeException("加载AccessProvider失败, 不存在登录管理器");
        }
        log.info("初始化AccessProvider集合数据:{}", JsonUtils.toJSONString(accessProviderList));
    }
}
