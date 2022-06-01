package com.ah.cloud.permissions.biz.domain.msg.push;

import com.ah.cloud.permissions.enums.DeviceTypeEnum;
import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;

/**
 * @program: permissions-center
 * @description: 消息推送实体
 * @author: YuKai Fan
 * @create: 2022-05-31 14:48
 **/
public interface MsgPush {

    /**
     * 消息标题
     * @return
     */
    String getTitle();

    /**
     * 消息内容
     * @return
     */
    String getContent();

    /**
     * 获取设备id, 多个用逗号隔开
     * @return
     */
    String getDeviceNo();

    /**
     * 获取设备类型
     * @return
     */
    MsgSourceEnum getMsgSourceEnum();

    /**
     * 推送方式
     * @return
     */
    PushMsgModeEnum getPushMsgModeEnum();
}
