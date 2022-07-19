package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-19
 */
public interface SysDictExtService extends IService<SysDict> {

    /**
     * 根据编码获取唯一字典
     * @param dictCode
     * @return
     */
    SysDict getOneByCode(String dictCode);
}
