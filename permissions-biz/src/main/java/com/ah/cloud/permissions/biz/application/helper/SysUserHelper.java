package com.ah.cloud.permissions.biz.application.helper;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Component
public class SysUserHelper {
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 构建系统用户实体
     * @param form
     * @return
     */
    public SysUser buildSysUserEntity(SysUserAddForm form) {
        SysUser sysUser = this.convertToEntity(form);
        sysUser.setUserId(this.generateUserId());
        sysUser.setPassword(this.encodePassword(PermissionsConstants.DEFAULT_PASSWORD));
        sysUser.setNickName(StringUtils.isEmpty(sysUser.getNickName())?sysUser.getNickName():generateNickName());
        sysUser.setStatus(UserStatusEnum.NORMAL.getStatus());
        return sysUser;
    }

    /**
     * 生成随机昵称
     * @return
     */
    public String generateNickName() {
        return RandomUtil.randomString(8);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUser convertToEntity(SysUserAddForm form) {
        return SysUserConvert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     * @param sysUser
     * @return
     */
    public SysUserVo convertToVo(SysUser sysUser) {
        return SysUserConvert.INSTANCE.convert(sysUser);
    }

    public PageResult<SysUserVo> convert2PageResult(PageInfo<SysUser> pageInfo) {
        PageResult<SysUserVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysUserConvert.INSTANCE.convert(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 生成用户id
     * @return
     */
    public Long generateUserId() {
        return AppUtils.randomLongId();
    }

    /**
     * 加密密码
     * @param oldPassword
     * @return
     */
    public String encodePassword(String oldPassword) {
        return passwordEncoder.encode(oldPassword);
    }

    public List<SysUserRole> buildSysUserRoleEntity(Long userId, List<String> roleCodeList) {
        List<SysUserRole> sysUserRoleList = Lists.newArrayList();
        for (String roleCode : roleCodeList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleCode(roleCode);
            sysUserRoleList.add(sysUserRole);
        }
        return sysUserRoleList;
    }

    @Mapper
    public interface SysUserConvert {
        SysUserHelper.SysUserConvert INSTANCE = Mappers.getMapper(SysUserHelper.SysUserConvert.class);

        SysUser convert(SysUserAddForm form);

        SysUserVo convert(SysUser sysUser);

        List<SysUserVo> convert(List<SysUser> sysUsers);
    }
}
