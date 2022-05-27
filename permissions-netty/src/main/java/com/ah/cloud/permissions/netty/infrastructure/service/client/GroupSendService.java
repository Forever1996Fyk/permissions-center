package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 17:13
 **/
@Slf4j
@Component
public class GroupSendService extends AbstractSendClientService<SingleSession> {
    private final static String LOG_MARK = "GroupSendService";

    @Override
    public <T> void sendAndAck(ImmutableTriple<SingleSessionKey, ServerSession, ServerSession> triple, MessageBody<T> body, Consumer<MessageBody<T>> afterHandle) {
        super.sendAndAck(triple, body, afterHandle);
    }

    @Override
    protected <T> Future<Void> doSend(SingleSession session, MessageBody<T> body) {
        return null;
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
