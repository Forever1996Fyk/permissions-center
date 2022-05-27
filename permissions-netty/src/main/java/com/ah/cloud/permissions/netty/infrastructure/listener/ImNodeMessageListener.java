package com.ah.cloud.permissions.netty.infrastructure.listener;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.infrastructure.util.IpUtils;
import com.ah.cloud.permissions.netty.application.helper.MessageHandleHelper;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.config.NettyProperties;
import com.ah.cloud.permissions.netty.infrastructure.service.client.SingleSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-17 16:35
 **/
@Slf4j
@Component
public class ImNodeMessageListener {
    @Resource
    private NettyProperties nettyProperties;
    @Resource
    private MessageHandleHelper messageHandleHelper;
    @Resource
    private SingleSendClientService singleSendClientService;
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;
    /**
     * 系统启动时开启
     */
    @PostConstruct
    public void start() {
        ThreadPoolManager.imNodeListenerThreadPool.execute(
                this::consumer
        );
    }

    private void consumer() {
        String imListenerNodeKey = RedisKeyHelper.getImListenerNodeKey(IpUtils.getHost(), nettyProperties.getPort());
        // redis pop操作是原子操作，所以只会有一个节点获取到数据
        MessageNodeDTO messageNode = redisCacheHandleStrategy.brpop(imListenerNodeKey, Duration.ofSeconds(Integer.MAX_VALUE));
        if (!Objects.isNull(messageNode)) {
            MessageBody<String> body = messageHandleHelper.convertToBody(messageNode);
            SingleSessionKey toSingleSessionKey = SingleSessionKey.builder().sessionId(messageNode.getToId()).msgSourceEnum(body.getMsgSourceEnum()).build();
            SingleSessionKey fromSingleSessionKey = SingleSessionKey.builder().sessionId(messageNode.getToId()).msgSourceEnum(body.getMsgSourceEnum()).build();
            SessionService<SingleSession> singleSessionService = SessionManager.getSingleSessionService();
            SingleSession toSingleSession = singleSessionService.get(toSingleSessionKey);
            SingleSession fromSingleSession = singleSessionService.get(fromSingleSessionKey);

            singleSendClientService.sendAndAck(ImmutableTriple.of(toSingleSessionKey, toSingleSession, fromSingleSession), body, message -> {});
        }
    }
}
