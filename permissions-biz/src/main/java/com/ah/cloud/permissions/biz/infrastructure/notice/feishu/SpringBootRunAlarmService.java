package com.ah.cloud.permissions.biz.infrastructure.notice.feishu;

import com.ah.cloud.permissions.biz.domain.notice.alarm.AlarmParam;
import com.ah.cloud.permissions.biz.domain.notice.alarm.feishu.GeneralFeishuBotAlarmMsg;
import com.ah.cloud.permissions.biz.domain.notice.content.feishu.TextContent;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.IpUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.FeishuMsgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 20:45
 **/
@Slf4j
@Component
public class SpringBootRunAlarmService extends AbstractFeishuAlarmService {
    private final static String LOG_MARK = "SpringBootRunAlarmService";

    private final static String SECRET = "kZSdQVzx6SlKDgDkyeuyLb";

    private final static String WEB_HOOK = "https://open.feishu.cn/open-apis/bot/v2/hook/86187c8f-4ec1-4362-aa00-6b8f2fb0fa33";

    public static final int MSG_MAX_LENGTH = 2048;

    @Value("${spring.application.name}")
    private String applicationName;


    @PostConstruct
    public void run() {
        long timestampSeconds = DateUtils.getCurrentSeconds();
        StringBuilder content = new StringBuilder()
                .append("============= 启动通知 ================\n")
                .append("应用名: ").append(applicationName)
                .append("\n")
                .append("启动ip: ").append(IpUtils.getHost())
                .append("\n")
                .append("启动时间: ").append(DateUtils.getStrCurrentTime()).append("\n")
                .append("<at user_id=\"all\">所有人</at> ")
                .append("\n");
        TextContent textContent = TextContent.builder().text(content.toString()).build();
        GeneralFeishuBotAlarmMsg alarmMsg = GeneralFeishuBotAlarmMsg.builder()
                .msg_type(FeishuMsgTypeEnum.TEXT.getType())
                .sign(genSign(SECRET, timestampSeconds))
                .timestamp(timestampSeconds)
                .content(textContent)
                .build();
        AlarmParam alarmParam = AlarmParam.builder().webHook(WEB_HOOK).content(alarmMsg).build();
        // 发送消息
        sendNotice(alarmParam);
        log.info("项目启动提醒 {}", JsonUtils.toJsonString(alarmMsg));

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
