package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserApi;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-21 22:25
 **/
public interface SysUserApiExtService extends IService<SysUserApi> {

    /**
     * 真-删除数据
     * @param queryWrapper
     * @return
     */
    int delete(Wrapper<SysUserApi> queryWrapper);
}
