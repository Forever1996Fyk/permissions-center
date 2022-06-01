package com.ah.cloud.permissions.biz.application.strategy.push.umeng;

import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.AndroidNotification;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.android.AndroidUnicast;
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
public class UmengAndroidUnicastBuilder extends AbstractUmengMsgCastBuilder {
    private static final String LOG_MARK = "UmengAndroidUnicastBuilder";

    @Override
    protected UmengNotification doBuilder(UmengMsgPush msgPush) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(umengMsgPushConfiguration.getAndroidAppKey(), umengMsgPushConfiguration.getAndroidAppMasterSecret());
        unicast.setDeviceToken(msgPush.getDeviceNo());
        unicast.setTicker("Android unicast ticker");
        unicast.setTitle(msgPush.getTitle());
        unicast.setText(msgPush.getContent());
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        unicast.setProductionMode();
        unicast.setExtraField("appPushJumpUrl", msgPush.getJumpUrl());
        unicast.setSound(msgPush.getVoiceCode());
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
        return MsgSourceEnum.ANDROID;
    }
}
