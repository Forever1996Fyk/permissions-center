package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.UserAuthorityHelper;
import com.ah.cloud.permissions.biz.application.service.SysUserRoleService;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
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
    private SysUserRoleService sysUserRoleService;
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
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getAccount, username)
                .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysUser)) {
            log.warn("UserAuthManager[createUserAuthorityByUsername] ==> 根据账号查询用户信息为空 ==> account:{}", username);
            return null;
        }

        /*
        数据转换成用户权限实体
         */
        UserAuthorityDTO userAuthorityDTO = userAuthorityHelper.convertDTO(sysUser);

        return userAuthorityDTO;
    }
}
