package com.ah.cloud.permissions.biz.infrastructure.sequence.producer;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.sequence.enums.ProducerTypeEnum;
import com.ah.cloud.permissions.biz.infrastructure.sequence.enums.SequenceErrorCodeEnum;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:46
 **/
@Slf4j
@Component
public class ProducerManager {
    /**
     * todo
     */
    private List<SequenceProducer> producerList = Lists.newArrayList();

    /**
     * 当前正在使用的类型
     */
    private final static ProducerTypeEnum USING_PRODUCER_TYPE = ProducerTypeEnum.DB;

    /**
     * 获取序列生成器
     * @param producerType
     * @return
     */
    public SequenceProducer getSequenceProducer(ProducerTypeEnum producerType) {
        return producerList.stream()
                .filter(sequenceProducer -> Objects.equals(sequenceProducer.getProducerType(), producerType))
                .findFirst()
                .orElseThrow(() -> new BizException(SequenceErrorCodeEnum.SEQUENCE_PRODUCER_NOT_EXISTED));
    }

    /**
     * 获取序列生成器
     * @return
     */
    public SequenceProducer getSequenceProducer() {
        return producerList.stream()
                .filter(sequenceProducer -> Objects.equals(sequenceProducer.getProducerType(), USING_PRODUCER_TYPE))
                .findFirst()
                .orElseThrow(() -> new BizException(SequenceErrorCodeEnum.SEQUENCE_PRODUCER_NOT_EXISTED));
    }

    public void asyncCacheSequenceCursor(String type, long cursor) {
        try {
            if (cursor > 0) {

            }
        } catch (Exception e) {
            log.error("ProducerManager[asyncCacheSequenceCursor] 容灾异步保存生成序列号失败, reason is {}, type is {}, cursor is {}", Throwables.getStackTraceAsString(e), type, cursor);
        }
    }
}
