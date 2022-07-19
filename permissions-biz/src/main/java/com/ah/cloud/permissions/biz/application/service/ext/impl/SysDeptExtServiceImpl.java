package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.ext.SysDeptExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDept;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysDeptMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:08
 **/
@Service
public class SysDeptExtServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptExtService {

    @Override
    public SysDept getOneByCode(String deptCode) {
        return baseMapper.selectOne(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getDeptCode, deptCode)
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
    }
}
