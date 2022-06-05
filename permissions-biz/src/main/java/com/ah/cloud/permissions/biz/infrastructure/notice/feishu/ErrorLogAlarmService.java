package com.ah.cloud.permissions.biz.infrastructure.notice.feishu;

import com.ah.cloud.permissions.biz.domain.notice.alarm.AlarmParam;
import com.ah.cloud.permissions.biz.domain.notice.alarm.feishu.GeneralFeishuBotAlarmMsg;
import com.ah.cloud.permissions.biz.domain.notice.content.feishu.TextContent;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.enums.FeishuMsgTypeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-04 22:34
 **/
public class ErrorLogAlarmService extends AbstractFeishuAlarmService {
    private final static String LOG_MARK = "ErrorLogAlarmService";

    private final static String SECRET = "I7dw6BamGTjRlZPhBLKiog";

    private final static String WEB_HOOK = "https://open.feishu.cn/open-apis/bot/v2/hook/f4c95939-9fa4-4b47-9cde-837f7b1eb6c9";

    public static final int MSG_MAX_LENGTH = 4096;

    public void send(String content) {
        long timestampSeconds = DateUtils.getCurrentSeconds();
        TextContent textContent = TextContent.builder().text(content).build();
        GeneralFeishuBotAlarmMsg alarmMsg = GeneralFeishuBotAlarmMsg.builder()
                .msg_type(FeishuMsgTypeEnum.TEXT.getType())
                .sign(genSign(SECRET, timestampSeconds))
                .timestamp(timestampSeconds)
                .content(textContent)
                .build();
        AlarmParam alarmParam = AlarmParam.builder().webHook(WEB_HOOK).content(alarmMsg).build();
        sendNotice(alarmParam);
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected int maxSize() {
        return MSG_MAX_LENGTH;
    }
}
