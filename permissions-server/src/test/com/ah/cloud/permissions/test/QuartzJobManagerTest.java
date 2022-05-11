package com.ah.cloud.permissions.test;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.message.QuartzJobChangeMessage;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.producer.QuartzJobChangeProducer;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import com.ah.cloud.server.consumer.redis.QuartzJobChangeConsumer;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 14:34
 **/
public class QuartzJobManagerTest extends BaseTest {
    @Resource
    private QuartzJobChangeProducer producer;

    @Test
    public void test() {
        producer.sendMessage(
                QuartzJobChangeMessage.builder()
                        .jobId(100L)
                        .changeTypeEnum(QuartzJobChangeTypeEnum.CHANGE_STATUS)
                        .build()
        );
    }
}
