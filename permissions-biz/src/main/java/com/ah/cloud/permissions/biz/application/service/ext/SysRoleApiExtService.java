package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-26 19:03
 **/
public interface SysRoleApiExtService extends IService<SysRoleApi> {

    /**
     * 真删除
     * @param wrapper
     * @return
     */
    int delete(Wrapper<SysRoleApi> wrapper);
}

