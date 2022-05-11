package com.ah.cloud.permissions.biz.infrastructure.mq.redis.producer;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisProducer;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.message.QuartzJobChangeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 09:54
 **/
@Component
public class QuartzJobChangeProducer extends AbstractBaseRedisProducer<QuartzJobChangeMessage> {

    /**
     * channel
     */
    @Value("${producer.redis.channel.name}")
    private String quartzJobChangeChannel;

    @Override
    protected String getChannel() {
        return quartzJobChangeChannel;
    }
}
