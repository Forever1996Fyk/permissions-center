package com.ah.cloud.permissions.biz.infrastructure.sequence.service;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:39
 **/
public interface SequenceTypeService {

    /**
     * 获取序列类型配置集合
     * @return
     */
    List<String> getSequenceTypeConfig();
}
