package com.ah.cloud.permissions.biz.application.strategy.push.umeng;

import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.ios.IOSUnicast;
import com.ah.cloud.permissions.enums.DeviceTypeEnum;
import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 18:21
 **/
@Component
public class UmengIOSUnicastBuilder extends AbstractUmengMsgCastBuilder {
    private static final String LOG_MARK = "UmengIOSUnicastBuilder";

    @Override
    protected UmengNotification doBuilder(UmengMsgPush msgPush) throws Exception {
        IOSUnicast unicast = new IOSUnicast(umengMsgPushConfiguration.getAndroidAppKey(), umengMsgPushConfiguration.getAndroidAppMasterSecret());
        unicast.setDeviceToken(msgPush.getDeviceNo());
        unicast.setDeviceToken(msgPush.getDeviceNo());
        unicast.setAlert(msgPush.getTitle(), "", msgPush.getContent());
        unicast.setBadge(0);
        unicast.setSound(msgPush.getVoiceCode() + IOS_VOICE_SUFFIX);
        unicast.setProductionMode();
        unicast.setCustomizedField("appPushJumpUrl", msgPush.getJumpUrl());
        return unicast;
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public PushMsgModeEnum getPushMsgModeEnum() {
        return PushMsgModeEnum.SINGLE_PUSH;
    }

    @Override
    public MsgSourceEnum getMsgSourceEnum() {
        return MsgSourceEnum.IOS;
    }
}
