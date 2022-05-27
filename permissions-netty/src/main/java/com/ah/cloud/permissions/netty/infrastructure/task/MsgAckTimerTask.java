package com.ah.cloud.permissions.netty.infrastructure.task;

import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 11:06
 **/
@Slf4j
@Getter
public class MsgAckTimerTask<T> implements TimerTask {

    private final AckDTO<T> ack;

    public MsgAckTimerTask(AckDTO<T> ack) {
        this.ack = ack;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        Integer sequenceId = ack.getSequenceId();
        log.info("MsgAckTimerTask[run] message ack send sequenceId is {}, retry is {}", sequenceId, ack.getRetry());
        Map<Integer, Timeout> ackMsgTimeoutList = SessionManager.getAckMsgTimeoutList();
        if (timeout.isCancelled()) {
            ackMsgTimeoutList.remove(sequenceId);
            return;
        }
        /*
        在ack列表中, 判断当前消息是否存在没有ack的消息,
        如果存在则发送ack, 否则直接结束当前任务时间轮
         */
        if (ackMsgTimeoutList.containsKey(sequenceId)) {
            if (ack.getRetry() > 0) {
                ack.getChannel().writeAndFlush(ack.getBody()).addListener(future -> {
                    if (future.isSuccess()) {
                        timeout.timer().newTimeout(new MsgAckTimerTask<>(ack), ack.getDuration(), TimeUnit.MILLISECONDS);
                    }
                });
            } else {
                ackMsgTimeoutList.remove(sequenceId);
                timeout.cancel();
            }
        } else {
            timeout.cancel();
        }
    }
}
