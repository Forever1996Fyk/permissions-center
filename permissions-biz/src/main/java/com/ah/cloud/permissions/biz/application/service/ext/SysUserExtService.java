package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-11 10:51
 **/
public interface SysUserExtService extends IService<SysUser> {

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    SysUser getOneByUserId(Long userId);
}
