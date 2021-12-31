package com.ah.cloud.permissions.security.application.hepler;

import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.security.domain.authority.DefaultAuthority;
import com.ah.cloud.permissions.security.domain.user.BaseUserInfo;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 当前登录用户信息
 * @author: YuKai Fan
 * @create: 2021-12-23 13:56
 **/
@Slf4j
@Component
public class LocalUserHelper {

    public LocalUser buildLocalUser(UserAuthorityDTO userAuthorityDTO) {
        BaseUserInfo baseUserInfo = BaseUserInfo.builder()
                .userId(userAuthorityDTO.getUserId())
                .build();
        return LocalUser.builder()
                .username(userAuthorityDTO.getAccount())
                .password(userAuthorityDTO.getPassword())
                .userInfo(baseUserInfo)
                .enabled(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .permissions(this.buildDefaultAuthorities(userAuthorityDTO.getAuthorities()))
                .build();
    }

    /**
     * 构建默认权限实体集合
     * @param authorities
     * @return
     */
    public Set<DefaultAuthority> buildDefaultAuthorities(Set<String> authorities) {
        return authorities.stream()
                .map(this::buildDefaultAuthority)
                .collect(Collectors.toSet());
    }

    /**
     * 构建默认权限实体
     * @param authority
     * @return
     */
    public DefaultAuthority buildDefaultAuthority(String authority) {
        return new DefaultAuthority(authority);
    }
}
