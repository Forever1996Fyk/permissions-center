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
public class GroupSendClientService extends SingleSendClientService {
    private final static String LOG_MARK = "GroupSendClientService";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
