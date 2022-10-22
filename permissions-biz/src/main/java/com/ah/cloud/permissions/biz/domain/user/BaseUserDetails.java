package com.ah.cloud.permissions.biz.domain.user;

import com.ah.cloud.permissions.biz.domain.token.Token;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/6 20:31
 **/
public interface BaseUserDetails<T extends Token> extends UserDetails {

    /**
     * token
     *
     * @return
     */
    T getToken();

    /**
     * 设置token
     * @param token
     */
    void setToken(T token);

}
