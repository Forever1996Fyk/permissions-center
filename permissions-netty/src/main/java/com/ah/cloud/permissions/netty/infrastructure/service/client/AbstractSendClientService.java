package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.domain.common.ImResult;
import com.ah.cloud.permissions.enums.netty.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.key.SessionKey;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.task.MsgAckTimerTask;
import com.google.common.base.Throwables;
import io.netty.channel.Channel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import java.util.List;
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
public abstract class AbstractSendClientService<K extends SessionKey, S extends ServerSession> implements SendClientService<K, S>, AckClientService, ComplexSendClientService<K, S> {

    /**
     * 默认 ack执行周期
     */
    private final static Integer DEFAULT_ACK_DURATION = 30;

    /**
     * 默认 ack重试次数
     */
    private final static Integer DEFAULT_ACK_RETRY = 5;


    /**
     * ack 执行周期
     */
    private Integer duration = DEFAULT_ACK_DURATION;

    /**
     * ack 重试次数
     */
    private Integer retry = DEFAULT_ACK_RETRY;

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
    public <T> void sendAndAck(final ImmutableTriple<K, S, Channel> triple, final MessageBody<T> body, Consumer<MessageBody<T>> afterHandle) {
        if (Objects.isNull(triple)) {
            log.error("{}[send] pair data error is empty", getLogMark());
            return;
        }
        K sessionKey = triple.getLeft();
        if (Objects.isNull(sessionKey)) {
            log.error("{}[send] session key is empty", getLogMark());
            return;
        }
        S toSession = triple.getMiddle();
        if (Objects.isNull(toSession)) {
            // 分发其他节点
            if (dispatchNode(sessionKey, body)) {
                // 消息ack
                ack(buildAckDTO(triple.getRight(), body));
                return;
            } else {
                throw new IMBizException(IMErrorCodeEnum.MSG_SEND_FAILED_SESSION_NOT_EXISTED);
            }
        }
        int sequenceId = AppUtils.generateSequenceId();
        body.setSequenceId(sequenceId);
        // 消息发送
        ImResult<Void> result = doSend(toSession, body);
        // 如果发送成功, 则ack
        if (result != null) {
            //消息发送成功后的操作
            afterHandle.accept(body);
            // 消息ack
            if (result.isSuccess()) {
                ack(buildAckDTO(triple.getRight(), body));
            }
        }
    }


    @Override
    public <T> void simpleSend(ImmutablePair<K, S> pair, MessageBody<T> body) {
        K sessionKey = pair.getLeft();
        S toSession = pair.getRight();
        if (Objects.isNull(toSession)) {
            dispatchNode(sessionKey, body);
            return;
        }
        int sequenceId = AppUtils.generateSequenceId();
        body.setSequenceId(sequenceId);
        // 消息发送
        doSend(toSession, body);
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
                .retry(getRetry())
                .duration(getDuration())
                .sequenceId(body.getSequenceId())
                .build();
        ack(ackDTO);
    }

    @Override
    public <T> void complexSendMessageBodyList(ImmutablePair<K, S> pair, List<MessageBody<T>> messageBodyList, Consumer<List<MessageBody<T>>> consumer) {
        S session = pair.getRight();
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
    protected abstract <T> ImResult<Void> doSend(S session, MessageBody<T> body);

    /**
     * 日志标志
     * @return
     */
    protected abstract String getLogMark();

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
                .retry(getRetry())
                .duration(getDuration())
                .sequenceId(body.getSequenceId())
                .build();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }
}
