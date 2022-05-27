package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.google.common.base.Throwables;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 14:31
 **/
@Slf4j
@Component
public class SingleSendClientService extends AbstractSendClientService<SingleSession> {
    private final static String LOG_MARK = "SingleSendClientService";

    @Override
    protected <T> Future<Void> doSend(SingleSession session, MessageBody<T> body) {
        Channel channel = session.getChannel();
        if (channel.isWritable()) {
            return channel.writeAndFlush(body);
        } else {
            log.info("SingleClientService[doSend] channel can not writeable, channel outbound buffer full");
            try {
                return channel.writeAndFlush(body).sync();
            } catch (InterruptedException e) {
                log.error("SingleClientService[doSend] message send failed, reason is {}", Throwables.getStackTraceAsString(e));
                throw new IMBizException(IMErrorCodeEnum.MSG_SEND_FAILED);
            }
        }
    }

    @Override
    protected boolean needThirdPush() {
        return true;
    }

    @Override
    protected boolean needRecordOfflineList() {
        return true;
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
