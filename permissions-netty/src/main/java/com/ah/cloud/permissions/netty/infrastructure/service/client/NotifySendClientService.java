package com.ah.cloud.permissions.netty.infrastructure.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: 消息通知
 * @author: YuKai Fan
 * @create: 2022-05-20 10:54
 **/
@Slf4j
@Component
public class NotifySendClientService extends SingleSendClientService {
    private final static String LOG_MARK = "NotifySendClientService";

    @Override
    protected boolean needRecordOfflineList() {
        return false;
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
