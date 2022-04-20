package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenuApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-26 19:04
 **/
public interface SysMenuApiExtService extends IService<SysMenuApi> {

    /**
     * 真删除
     * @param wrapper
     * @return
     */
    int delete(Wrapper<SysMenuApi> wrapper);
}

