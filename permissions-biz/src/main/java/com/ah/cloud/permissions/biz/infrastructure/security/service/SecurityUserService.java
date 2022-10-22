package com.ah.cloud.permissions.biz.infrastructure.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 12:19
 **/
public interface SecurityUserService<U extends UserDetails> {

    /**
     * 保存用户信息
     * @param u
     */
    void saveUserDetails(U u);

    /**
     * 获取当前Security 用户信息
     * @param token
     * @return
     */
     U getUserDetails(String token);

    /**
     * 删除当前Security 用户信息
     * @param key
     */
    void deleteUserDetails(String key);

    /**
     * 刷新用户信息过期时间
     * @param token
     */
    void refreshExpire(String token);

    /**
     * 处理登录用户信息
     * @param u
     */
    void authenticationUserDetails(U u);
}
