package com.ah.cloud.permissions.biz.infrastructure.sequence.repository;

import com.ah.cloud.permissions.biz.infrastructure.sequence.model.BizSequence;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:43
 **/
public interface SequenceRepository {

    /**
     * 获取序列
     * @param type
     * @return
     */
    BizSequence getBizSequence(String type);
}
