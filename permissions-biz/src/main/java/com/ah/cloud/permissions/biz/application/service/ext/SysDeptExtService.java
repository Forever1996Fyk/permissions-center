package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:08
 **/
public interface SysDeptExtService extends IService<SysDept> {

    /**
     * 根据编码获取唯一数据
     * @param deptCode
     * @return
     */
    SysDept getOneByCode(String deptCode);
}
