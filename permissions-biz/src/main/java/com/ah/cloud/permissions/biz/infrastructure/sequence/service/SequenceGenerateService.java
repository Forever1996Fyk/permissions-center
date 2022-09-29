package com.ah.cloud.permissions.biz.infrastructure.sequence.service;

import com.ah.cloud.permissions.biz.infrastructure.sequence.model.BizSequence;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:10
 **/
public interface SequenceGenerateService {

    /**
     * 根据业务类型生成业务id
     *
     * 用于申请一个订单ID，订单ID是根据用户ID的后3位加上又发号服务生成的全局增加数字组合而成，订单保存时使用订单ID的后3位也就是用户ID的后3位mod256进行分库分表。
     * @param bizType
     * @return
     */
    BizSequence genBizId(String bizType);

}
