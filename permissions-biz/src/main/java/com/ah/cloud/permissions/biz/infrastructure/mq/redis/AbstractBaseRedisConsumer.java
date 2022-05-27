package com.ah.cloud.permissions.biz.infrastructure.mq.redis;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 10:55
 **/
@Slf4j
@Component
public abstract class AbstractBaseRedisConsumer<T> implements MessageListener, InitializingBean {
    @Resource
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(pattern);
        T t = convert(message.getBody());
        try {
            doHandleMessage(t);
            log.info("{} handle message success, params is {}", channel, JsonUtils.toJSONString(t));
        } catch (Throwable throwable) {
            log.info("{} handle message is error with throwable, params is {}, reason is {} ", channel, JsonUtils.toJSONString(t), Throwables.getStackTraceAsString(throwable));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisMessageListenerContainer.addMessageListener(getMessageListener(), getTopic());
    }

    /**
     * 获取 topic
     * @return
     */
    protected abstract Topic getTopic();

    /**
     * 获取监听器
     * @return
     */
    protected abstract MessageListener getMessageListener();

    /**
     * 处理消息
     * @param t
     */
    protected abstract void doHandleMessage(T t);

    /**
     * 转换实体
     * @param bytes
     * @return
     */
    protected abstract T convert(byte[] bytes);
}
