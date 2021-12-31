package com.ah.cloud.permissions.security.service.impl;

import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.security.application.checker.LocalUserChecker;
import com.ah.cloud.permissions.security.application.hepler.LocalUserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-22 15:15
 **/
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserAuthManager userAuthManager;
    @Autowired
    private LocalUserHelper localUserHelper;
    @Autowired
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
