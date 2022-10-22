package com.ah.cloud.permissions.biz.domain.user;

import com.ah.cloud.permissions.biz.domain.token.AccessToken;
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
public class LocalUser implements BaseUserDetails<AccessToken> {
    /**
     * 账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户基本信息
     */
    private BaseUserInfo userInfo;

    /**
     * 用户权限信息
     */
    private AuthorityInfo authorityInfo;

    /**
     * 设备信息
     */
    private DeviceInfo deviceInfo;

    /**
     * 认证是否过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 账号是否可用
     */
    private boolean enabled;

    /**
     * 账号没有过期
     */
    private boolean accountNonExpired;

    /**
     * 账号没有锁定
     */
    private boolean accountNonLocked;

    /**
     * 权限是否变更
     */
    private boolean authorityChanged;

    /**
     * 用户token
     */
    private AccessToken token;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
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
        return authorityInfo.getAuthorities();
    }
}
