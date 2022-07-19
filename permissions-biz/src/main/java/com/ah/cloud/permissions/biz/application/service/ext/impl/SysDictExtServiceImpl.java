package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.SysDictService;
import com.ah.cloud.permissions.biz.application.service.ext.SysDictExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDict;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.SysDictMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-19
 */
@Service
public class SysDictExtServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictExtService {

    @Override
    public SysDict getOneByCode(String dictCode) {
        return baseMapper.selectOne(
                new QueryWrapper<SysDict>().lambda()
                        .eq(SysDict::getDictCode, dictCode)
                        .eq(SysDict::getDeleted, DeletedEnum.NO.value)
        );
    }
}
