package com.ah.cloud.permissions.biz.infrastructure.security.service.impl;

import com.ah.cloud.permissions.biz.application.checker.LocalUserChecker;
import com.ah.cloud.permissions.biz.application.helper.LocalUserHelper;
import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-06 21:35
 **/
@Slf4j
@Component
public class ValidateCodeUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserAuthManager userAuthManager;
    @Resource
    private LocalUserHelper localUserHelper;
    @Resource
    private LocalUserChecker localUserChecker;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthorityDTO userAuthorityDTO = userAuthManager.createUserAuthorityByUsername(username);
        /*
        1. 用户数据校验
        2. 用户状态校验
        3. 用户权限校验
         */
        localUserChecker.checkUserAuthority(userAuthorityDTO);

        /*
        构建当前登录用户信息
         */
        return localUserHelper.buildLocalUser(userAuthorityDTO);
    }
}
