package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysUserChecker;
import com.ah.cloud.permissions.biz.application.helper.SysUserHelper;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 系统用户管理器
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Slf4j
@Component
public class SysUserManager {
    @Autowired
    private SysUserHelper sysUserHelper;
    @Autowired
    private SysUserChecker sysUserChecker;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加新用户
     * @param form
     */
    public void addSysUser(SysUserAddForm form) {
        /*
        校验用户手机号是否已存在
         */
        sysUserChecker.checkUserIsExist(form.getPhone());
        /*
        数据转换
         */
        SysUser sysUser = sysUserHelper.buildSysUserEntity(form);
        sysUserService.save(sysUser);
    }

    /**
     * 逻辑删除用户
     * @param id
     */
    public void deleteSysUserById(Long id) {
        /*
        获取当前用户
         */
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        /*
        更新用户删除标识
         */
        sysUser.setDeleted(id);
        sysUser.setModifier(String.valueOf(SecurityUtils.getBaseUserInfo().getUserId()));
        sysUserService.updateById(sysUser);

        /*
        逻辑删除用户角色
         */
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    public SysUserVo findSysUserById(Long id) {
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return sysUserHelper.convertToVo(sysUser);
    }

    /**
     * 分页查询用户信息
     * @param query
     * @return
     */
    public PageResult<SysUserVo> pageSysUsers(SysUserQuery query) {
        PageInfo<SysUser> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserService.list(
                                new QueryWrapper<SysUser>().lambda()
                                        .like(
                                                !StringUtils.isEmpty(query.getNickName()),
                                                SysUser::getNickName, query.getNickName())
                                        .eq(
                                                !StringUtils.isEmpty(query.getPhone()),
                                                SysUser::getPhone, query.getPhone())
                                        .like(
                                                !StringUtils.isEmpty(query.getEmail()),
                                                SysUser::getEmail, query.getEmail())
                                        .eq(
                                                SysUser::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        PageResult<SysUserVo> result = sysUserHelper.convert2PageResult(pageInfo);
        return result;
    }
}
