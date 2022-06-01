package com.ah.cloud.permissions.biz.application.strategy.push;

import com.ah.cloud.permissions.biz.domain.msg.push.MsgPush;
import com.ah.cloud.permissions.domain.common.MsgPushResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description: 消息推送
 * @author: YuKai Fan
 * @create: 2022-05-31 14:41
 **/
public interface MsgPushService {

    /**
     * app push消息推送
     * @param msgPush
     * @return
     */
    void sendAppPushMsg(MsgPush msgPush);

    /**
     * 是否支持
     * @param msgPush
     * @return
     */
    boolean support(MsgPush msgPush);
}
