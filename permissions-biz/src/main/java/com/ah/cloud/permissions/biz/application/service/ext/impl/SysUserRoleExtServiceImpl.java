package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.SysUserRoleService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-01-04
 */
@Service
public class SysUserRoleExtServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleExtService {

    @Override
    public int delete(Wrapper<SysUserRole> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }
}
