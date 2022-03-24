package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-21 22:25
 **/
public interface SysUserRoleExtService extends IService<SysUserRole> {

    /**
     * 真-删除数据
     * @param queryWrapper
     * @return
     */
    int delete(Wrapper<SysUserRole> queryWrapper);
}
