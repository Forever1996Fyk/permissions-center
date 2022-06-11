package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.ext.SysUserExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysUserMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-11 10:51
 **/
@Service
public class SysUserExtServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserExtService {

    @Override
    public SysUser getOneByUserId(Long userId) {
        return baseMapper.selectOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getUserId, userId)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
    }
}
