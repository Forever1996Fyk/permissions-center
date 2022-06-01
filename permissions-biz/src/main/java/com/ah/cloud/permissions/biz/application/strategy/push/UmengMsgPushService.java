package com.ah.cloud.permissions.biz.application.strategy.push;

import com.ah.cloud.permissions.biz.domain.msg.push.MsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.application.strategy.push.client.UmengPushClient;
import com.ah.cloud.permissions.biz.application.strategy.selector.UmengMsgCastBuilderSelector;
import com.ah.cloud.permissions.enums.common.push.UmengErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 16:49
 **/
@Component
public class UmengMsgPushService extends AbstractMsgPushService {
    private final static String LOG_MARK = "UmengMsgPushService";
    @Resource
    private UmengPushClient umengPushClient;
    @Resource
    private UmengMsgCastBuilderSelector selector;

    @Override
    protected void pushMsg(MsgPush msgPush) {
        try {
            UmengMsgPush umengMsgPush = (UmengMsgPush) msgPush;
            umengPushClient.send(selector.build(umengMsgPush));
        } catch (Exception e) {
            throw new BizException(UmengErrorCodeEnum.MSG_APP_PUSH_FAIL);
        }
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public boolean support(MsgPush msgPush) {
        return UmengMsgPush.class.isAssignableFrom(msgPush.getClass());
    }
}
