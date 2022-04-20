package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.UserAuthorityHelper;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 用户权限管理
 * @author: YuKai Fan
 * @create: 2021-12-22 18:09
 **/
@Slf4j
@Component
public class UserAuthManager {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private UserAuthorityHelper userAuthorityHelper;

    /**
     * 根据用户账号 构造用户权限实体数据
     *
     * @param username
     * @return
     */
    public UserAuthorityDTO createUserAuthorityByUsername(String username) {
        /*
        1. 根据账号查询用户信息
         */
        SysUser sysUser = sysUserService.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getAccount, username)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysUser)) {
            log.warn("UserAuthManager[createUserAuthorityByUsername] query SysUser is empty by account={}", username);
            throw new BizException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_ERROR, username);
        }
        /*
        数据转换成用户权限实体
        该操作查询的次数较多, 根据实际的请求并发数进行不同的技术优化
        1、提前把用户对应的menu, api, role数据加载到缓存中(内存/redis)
        2、使用线程池进行异步查询, 最后汇总
         */
        UserAuthorityDTO userAuthorityDTO = userAuthorityHelper.convertDTO(sysUser);
        Set<String> roleCodeSet = userAuthorityHelper.buildRoleCodeSet(sysUser.getUserId());
        Set<Long> menuIdSet = userAuthorityHelper.buildMenuIdSet(sysUser.getUserId(), roleCodeSet);
        Set<String> apiCodeSet = userAuthorityHelper.buildApiCodeSet(sysUser.getUserId(), roleCodeSet, menuIdSet);
        userAuthorityDTO.setAuthorities(apiCodeSet);
        userAuthorityDTO.setRoleCodeSet(roleCodeSet);
        userAuthorityDTO.setMenuIdSet(menuIdSet);
        return userAuthorityDTO;
    }
}
