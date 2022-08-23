package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserIntro;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 22:13
 **/
public interface SysUserIntroExtService extends IService<SysUserIntro> {

    /**
     * 根据用户id获取用户简介
     * @param userId
     * @return
     */
    SysUserIntro getOneByUserId(Long userId);
}
