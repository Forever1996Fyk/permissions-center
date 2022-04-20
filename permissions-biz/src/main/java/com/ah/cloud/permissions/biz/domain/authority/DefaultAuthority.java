package com.ah.cloud.permissions.biz.domain.authority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @program: permissions-center
 * @description: 默认的权限实体
 * @author: YuKai Fan
 * @create: 2021-12-17 17:42
 **/
@NoArgsConstructor
@AllArgsConstructor
public class DefaultAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 权限标识
     */
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
