package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:41
 **/
public interface EdiTypeService {

    /**
     * edi类型
     *
     * @return
     */
    EdiTypeEnum initType();
}
