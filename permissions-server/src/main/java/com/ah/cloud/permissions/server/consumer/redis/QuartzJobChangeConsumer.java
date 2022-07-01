package com.ah.cloud.permissions.server.consumer.redis;

import com.ah.cloud.permissions.biz.application.strategy.quartz.QuartzJobChangeService;
import com.ah.cloud.permissions.biz.application.strategy.selector.QuartzJobChangeServiceSelector;
import com.ah.cloud.permissions.biz.domain.quartz.dto.QuartzJobChangeDTO;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisConsumer;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.message.QuartzJobChangeMessage;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:10
 **/
@Component
public class QuartzJobChangeConsumer extends AbstractBaseRedisConsumer<QuartzJobChangeMessage> {
    @Resource
    private QuartzJobChangeServiceSelector selector;
    /**
     * channel
     */
    @Value("${mq.redis.topic.quartzJobChange.name}")
    private String quartzJobChangeChannel;

    @Override
    protected Topic getTopic() {
        return new ChannelTopic(quartzJobChangeChannel);
    }

    @Override
    protected MessageListener getMessageListener() {
        return this;
    }

    @Override
    protected void doHandleMessage(QuartzJobChangeMessage quartzJobChangeMessage) {
        QuartzJobChangeService quartzJobChangeService = selector.select(quartzJobChangeMessage.getChangeTypeEnum());
        quartzJobChangeService.handleChange(
                QuartzJobChangeDTO.builder()
                        .changeTypeEnum(quartzJobChangeMessage.getChangeTypeEnum())
                        .jobId(quartzJobChangeMessage.getJobId())
                        .build()
        );
    }

    @Override
    protected QuartzJobChangeMessage convert(byte[] bytes) {
        return JsonUtils.byteToBean(bytes, QuartzJobChangeMessage.class);
    }
}
