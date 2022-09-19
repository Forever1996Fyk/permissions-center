package com.ah.cloud.permissions.biz.infrastructure.sequence.repository.impl;

import com.ah.cloud.permissions.biz.infrastructure.sequence.model.BizSequence;
import com.ah.cloud.permissions.biz.infrastructure.sequence.producer.ProducerManager;
import com.ah.cloud.permissions.biz.infrastructure.sequence.producer.SequenceProducer;
import com.ah.cloud.permissions.biz.infrastructure.sequence.repository.SequenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:44
 **/
@Slf4j
@Repository
public class SequenceRepositoryImpl implements SequenceRepository {
    @Resource
    private ProducerManager producerManager;

    @Override
    public BizSequence getBizSequence(String type) {
        SequenceProducer producer = producerManager.getSequenceProducer();
        // 生成序列号
        BizSequence bizSequence = producer.generateSequence(type);
        // 容灾异步保存生成序列号
        return null;
    }
}
