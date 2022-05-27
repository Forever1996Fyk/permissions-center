package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.application.helper.SessionHelper;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.DistributionSession;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.task.MsgAckTimerTask;
import com.google.common.base.Throwables;
import io.netty.channel.Channel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 10:51
 **/
@Slf4j
@Component
public abstract class AbstractSendClientService<S extends ServerSession> implements SendClientService, AckClientService, ComplexSendClientService<S> {

    @Value(value = "${im.ack.duration}")
    private Integer duration;

    @Value(value = "${im.ack.retry}")
    private Integer retry;

    @Resource
    private SessionHelper sessionHelper;

    @Resource
    private MessageStoreManager messageStoreManager;

    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    /**
     * 发消息ack 时间轮
     * HashedWheelTimer netty听的时间轮算法
     * threadFactory: 默认线程工厂, 创建工作线程
     * tickDuration: 时间刻度的长度, 多长时间tick一次
     * ticksPerWheel: 时间轮的长度，一圈下来有多少格
     * leakDetection: 默认为true, 开启泄露检测
     * maxPendingTimeouts: 最大挂起Timeouts数量
     * <p>
     * 具体原理可参考博客: https://blog.csdn.net/dtlscsl/article/details/94185614
     */
    protected final Timer ackTimer = new HashedWheelTimer(
            new DefaultThreadFactory("send-msg-ack-timer-websocket")
            , 100
            , TimeUnit.MILLISECONDS
            , 1024
            , false);

    @Override
    public <T> void sendAndAck(final ImmutableTriple<SingleSessionKey, ServerSession, ServerSession> triple, final MessageBody<T> body, Consumer<MessageBody<T>> afterHandle) {
        if (Objects.isNull(triple)) {
            log.error("{}[send] pair data error is empty", getLogMark());
            return;
        }
        SingleSessionKey singleSessionKey = triple.getLeft();
        if (Objects.isNull(singleSessionKey)) {
            log.error("{}[send] session key is empty", getLogMark());
            return;
        }
        ServerSession session = triple.getMiddle();
        if (Objects.isNull(session)) {
            // 分发其他节点
            dispatchNode(singleSessionKey, body);
            // 消息ack
            ack(buildAckDTO(triple.getRight().getChannel(), body));
            return;
        }
        if (Objects.isNull(session.getChannel())) {
            log.error("{}[send] session not initialize start reconnect, body is {}", getLogMark(), JsonUtils.toJSONString(body));
            // 如果channel为空，则执行重新连接 todo
//            reconnect(session);
        }
        S s = (S) session;
        int sequenceId = AppUtils.generateSequenceId();
        body.setSequenceId(sequenceId);
        // 消息发送
        Future<Void> result = doSend(s, body);
        // 如果发送成功, 则ack
        if (result != null) {
            //消息发送成功后的操作
            afterHandle.accept(body);

            // 消息ack
            result.addListener(future -> {
                if (future.isSuccess()) {
                    ack(buildAckDTO(triple.getRight().getChannel(), body));
                }
            });
        }
    }

    @Override
    public <T> void simpleSend(ImmutablePair<SingleSessionKey, ServerSession> pair, MessageBody<T> body) {
        SingleSessionKey singleSessionKey = pair.getLeft();
        ServerSession session = pair.getRight();
        if (Objects.isNull(session)) {
            dispatchNode(singleSessionKey, body);
            return;
        }
        int sequenceId = AppUtils.generateSequenceId();
        body.setSequenceId(sequenceId);
        // 消息发送
        S s = (S) session;
        doSend(s, body);
    }

    /**
     * 构建ack数据
     *
     * @param channel
     * @param body
     * @param <T>
     * @return
     */
    private <T> AckDTO<T> buildAckDTO(Channel channel, MessageBody<T> body) {
        return AckDTO.<T>builder()
                .body(body)
                .channel(channel)
                .retry(retry)
                .duration(duration)
                .sequenceId(body.getSequenceId())
                .build();
    }

    @Override
    public <T> void ack(AckDTO<T> ackDTO) {
        Timeout timeout = ackTimer.newTimeout(new MsgAckTimerTask<>(ackDTO), ackDTO.getDuration(), TimeUnit.MILLISECONDS);
        SessionManager.getAckMsgTimeoutList().put(ackDTO.getSequenceId(), timeout);
    }

    @Override
    public <T> void ack(Channel channel, MessageBody<T> body) {
        AckDTO<T> ackDTO = AckDTO.<T>builder()
                .body(body)
                .channel(channel)
                .retry(retry)
                .duration(duration)
                .sequenceId(body.getSequenceId())
                .build();
        ack(ackDTO);
    }

    /**
     * 分发节点
     * @param singleSessionKey
     * @param body
     * @param <T>
     */
    @Override
    public <T> void dispatchNode(SingleSessionKey singleSessionKey, MessageBody<T> body) {
        /*
        1. 判断当前用户是否存在其他节点
            1.1 如果存在则通过监听redis 触发其他节点发送消息
            1.2 如果不存在则发送消息推送push, 并且存入离线消息列表
         */
        DistributionSession distributionSession = redisCacheHandleStrategy.getCacheObject(RedisKeyHelper.getImDistributionSessionKey(singleSessionKey.getSessionId()));
        if (Objects.isNull(distributionSession)) {
            if (needThirdPush()) {
                // 第三方推送 TODO

            }
            if (needRecordOfflineList()) {
                // 是否记录离线列表
                ThreadPoolManager.offlineMessageStoreThreadPool.execute(() -> messageStoreManager.offlineMessageStore(body));
            }
        } else {
            String imListenerNodeKey = RedisKeyHelper.getImListenerNodeKey(distributionSession.getHost(), distributionSession.getPort());
            redisCacheHandleStrategy.lpush(imListenerNodeKey, sessionHelper.buildMessageNodeDTO(body));
            log.info("{}[send] message send other node is {}", getLogMark(), imListenerNodeKey);
        }
    }

    @Override
    public <T> void complexSendMessageBodyList(ImmutablePair<SingleSessionKey, S> pair, List<MessageBody<T>> messageBodyList, Consumer<List<MessageBody<T>>> consumer) {
        ServerSession session = pair.getRight();
        Channel channel = session.getChannel();
        if (channel.isWritable()) {
            channel.writeAndFlush(messageBodyList);
        } else {
            log.info("{}[doSend] channel can not writeable, channel outbound buffer full", getLogMark());
            try {
                channel.writeAndFlush(messageBodyList).sync();
            } catch (InterruptedException e) {
                log.error("{}[doSend] message send failed, reason is {}", getLogMark(), Throwables.getStackTraceAsString(e));
                throw new IMBizException(IMErrorCodeEnum.MSG_SEND_FAILED);
            }
        }
        consumer.accept(messageBodyList);
    }

    /**
     * 消息发送
     *
     * @param session
     * @param body
     * @param <T>
     * @return
     */
    protected abstract <T> Future<Void> doSend(S session, MessageBody<T> body);

    /**
     * 是否需要第三方推送
     *
     * @return
     */
    protected abstract boolean needThirdPush();

    /**
     * 是否需要记录离线列表
     *
     * @return
     */
    protected abstract boolean needRecordOfflineList();

    /**
     * 日志标志
     * @return
     */
    protected abstract String getLogMark();
}
