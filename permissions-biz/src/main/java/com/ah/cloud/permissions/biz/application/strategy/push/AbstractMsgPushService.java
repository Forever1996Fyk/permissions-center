package com.ah.cloud.permissions.biz.application.strategy.push;

import com.ah.cloud.permissions.biz.domain.msg.push.MsgPush;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 14:54
 **/
@Slf4j
@Component
public abstract class AbstractMsgPushService implements MsgPushService {

    @Override
    public void sendAppPushMsg(MsgPush msgPush) {
        log.info("{}[sendAppPushMsg] start handle push msg, params is {}", getLogMark(), JsonUtils.toJSONString(msgPush));
        try {
            pushMsg(msgPush);
        } catch (BizException bizException) {
            log.error("{}[sendAppPushMsg] push msg error with bizException, params is {}, reason is {}", getLogMark(), JsonUtils.toJSONString(msgPush), Throwables.getStackTraceAsString(bizException));
            throw bizException;
        } catch (Throwable throwable) {
            log.error("{}[sendAppPushMsg] push msg error with throwable, params is {}, reason is {}", getLogMark(), JsonUtils.toJSONString(msgPush), Throwables.getStackTraceAsString(throwable));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 推送消息
     * @param msgPush
     * @return
     */
    protected abstract void pushMsg(MsgPush msgPush);

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();
}
