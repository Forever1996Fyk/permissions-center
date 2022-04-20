package com.ah.cloud.permissions.biz.domain.user;

import com.ah.cloud.permissions.biz.domain.authority.DefaultAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @program: permissions-center
 * @description: 用户权限信息
 * @author: YuKai Fan
 * @create: 2022-04-05 20:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityInfo {

    /**
     * 用户权限集合
     */
    private Set<DefaultAuthority> authorities;

    /**
     * 角色编码集合
     */
    private Set<String> roleCodeSet;

    /**
     * 菜单集合
     */
    private Set<Long> menuIdSet;
}
