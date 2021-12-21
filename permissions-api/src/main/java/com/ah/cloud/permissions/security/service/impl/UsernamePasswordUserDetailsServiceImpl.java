package com.ah.cloud.permissions.security.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: 账号密码 loads user-specific data
 * @author: YuKai Fan
 * @create: 2021-12-17 16:26
 **/
@Component
public class UsernamePasswordUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
