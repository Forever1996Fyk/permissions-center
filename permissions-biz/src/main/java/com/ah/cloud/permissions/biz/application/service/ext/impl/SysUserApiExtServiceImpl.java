package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.ext.SysUserApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysUserApiMapper;
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
public class SysUserApiExtServiceImpl extends ServiceImpl<SysUserApiMapper, SysUserApi> implements SysUserApiExtService {

    @Override
    public int delete(Wrapper<SysUserApi> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }
}
