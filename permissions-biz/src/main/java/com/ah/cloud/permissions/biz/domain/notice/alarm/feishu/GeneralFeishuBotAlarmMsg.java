package com.ah.cloud.permissions.biz.domain.notice.alarm.feishu;

import com.ah.cloud.permissions.biz.domain.notice.content.feishu.TextContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 17:52
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralFeishuBotAlarmMsg implements FeishuBotAlarmMsg<TextContent> {

    /**
     * 消息类型
     */
    private String msg_type;

    /**
     * 签名
     */
    private String sign;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 文本内容
     */
    private TextContent content;
}
