package com.ah.cloud.permissions.biz.domain.msg.push;

import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 16:50
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmengMsgPush implements MsgPush {

    /**
     * 标题
     */
    private String title;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 设备id
     */
    private String deviceNo;

    /**
     * 设备类型
     */
    private MsgSourceEnum msgSourceEnum;

    /**
     * 推送方式
     */
    private PushMsgModeEnum pushMsgModeEnum;

    /**
     * 跳转url
     */
    private String jumpUrl;

    /**
     * 播放声音
     */
    private String voiceCode;
}
