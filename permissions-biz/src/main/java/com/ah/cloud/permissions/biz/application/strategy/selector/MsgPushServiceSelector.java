package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.push.MsgPushService;
import com.ah.cloud.permissions.biz.domain.msg.push.MsgPush;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 21:46
 **/
@Slf4j
@Component
public class MsgPushServiceSelector {
    @Resource
    private List<MsgPushService> list;

    public void sendPushMsg(MsgPush msgPush) {
        int currentPosition = 0;
        int size = list.size();
        for (MsgPushService msgPushService : list) {
            if (!msgPushService.support(msgPush)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("MsgPushServiceSelector request with %s (%d/%d)",
                        msgPush.getClass().getSimpleName(), ++currentPosition, size)));
            }
            msgPushService.sendAppPushMsg(msgPush);
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "MsgPushServiceSelector");
    }
}
