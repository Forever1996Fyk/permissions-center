package com.ah.cloud.permissions.biz.infrastructure.sequence.producer;

import com.ah.cloud.permissions.biz.infrastructure.sequence.enums.ProducerTypeEnum;
import com.ah.cloud.permissions.biz.infrastructure.sequence.model.BizSequence;

/**
 * @program: permissions-center
 * @description: 序列生产者
 * @author: YuKai Fan
 * @create: 2022/9/16 15:45
 **/
public interface SequenceProducer {

    /**
     * 获取生产类型
     * @return
     */
    ProducerTypeEnum getProducerType();

    /**
     * 生成序列
     *
     * @param type 业类型
     * @return 序列号
     */
    BizSequence generateSequence(String type);

    /**
     * 初始化序列号生成
     *
     * @param type      类型
     * @param increment 初始化序列号增量
     */
    void initSequencePersistence(String type, long increment);

    /**
     * 检查可用性
     *
     * @param type 类型
     * @return 检查结果
     */
    boolean checkAvailable(String type);

    /**
     * 获取当前序列号最大游标
     *
     * @param type
     * @return 序列号最大游标
     */
    default long getMaxSequenceCursor(String type) {
        return 0;
    }

    /**
     * 初始化起始值
     * @param type
     * @param startCursor
     */
    void initDefaultCursor(String type, long startCursor);

    /**
     * 清理历史数据
     *
     * @param type       类型
     * @param endCursor  结束游标
     * @param limitCount 每次限定数量
     */
    default void clearHistory(String type, long endCursor, int limitCount) {
    }
}
