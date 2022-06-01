package com.ah.cloud.permissions.biz.application.strategy.push.umeng;

import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.application.strategy.push.MsgEntityBuilder;
import com.ah.cloud.permissions.enums.DeviceTypeEnum;
import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 18:16
 **/
public interface UmengMsgCastBuilder extends MsgEntityBuilder<UmengMsgPush, UmengNotification> {

    /**
     * 推送方式
     * @return
     */
    PushMsgModeEnum getPushMsgModeEnum();

    /**
     * 设备类型
     * @return
     */
    MsgSourceEnum getMsgSourceEnum();
}
