package com.ah.cloud.permissions.security.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description: 本地用户基本信息
 * @author: YuKai Fan
 * @create: 2021-12-17 17:04
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUser implements UserDetails {
    /**
     * 账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 认证是否过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 账号是否可用
     */
    private Boolean enabled;

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> permissions;

    /**
     * 用户基本信息
     */
    private BaseUserInfo userInfo;

    /**
     * 当前用户的登录token
     */
    private String accessToken;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }
}
