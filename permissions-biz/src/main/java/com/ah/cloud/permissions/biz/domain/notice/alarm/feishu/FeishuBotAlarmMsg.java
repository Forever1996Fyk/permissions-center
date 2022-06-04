package com.ah.cloud.permissions.biz.domain.notice.alarm.feishu;

import com.ah.cloud.permissions.biz.domain.notice.alarm.AlarmMsg;
import com.ah.cloud.permissions.biz.domain.notice.content.feishu.FeishuContent;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 17:33
 **/
public interface FeishuBotAlarmMsg<T extends FeishuContent> extends AlarmMsg {

    /**
     * 获取消息类型
     * @return
     */
    String getMsg_type();

    /**
     * 获取时间戳
     * @return
     */
    Long getTimestamp();

    /**
     * 获取签名
     * @return
     */
    String getSign();

    /**
     * 获取内容
     * @return
     */
    T getContent();
}
