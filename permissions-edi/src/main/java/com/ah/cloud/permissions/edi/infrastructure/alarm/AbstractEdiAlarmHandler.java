package com.ah.cloud.permissions.edi.infrastructure.alarm;

import com.ah.cloud.permissions.biz.domain.notice.alarm.AlarmParam;
import com.ah.cloud.permissions.biz.domain.notice.alarm.feishu.GeneralFeishuBotAlarmMsg;
import com.ah.cloud.permissions.biz.domain.notice.content.feishu.TextContent;
import com.ah.cloud.permissions.biz.infrastructure.notice.feishu.AbstractFeishuAlarmService;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizRecordAdapterService;
import com.ah.cloud.permissions.edi.domain.alarm.EdiAlarmInfo;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordCountQuery;
import com.ah.cloud.permissions.edi.infrastructure.config.RetryConfiguration;
import com.ah.cloud.permissions.enums.FeishuMsgTypeEnum;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 18:11
 **/
@Slf4j
@Component
public abstract class AbstractEdiAlarmHandler extends AbstractFeishuAlarmService implements EdiAlarmHandler {
    @Value("${spring.application.name")
    protected String applicationName;
    @Value("${spring.profiles.active}")
    protected String env;
    @Resource
    protected RetryConfiguration retryConfiguration;
    @Resource
    protected RetryBizRecordAdapterService retryBizRecordAdapterService;

    @Override
    public void alarm(List<RetryBizConfigDTO> retryBizConfigDTOList) {
        log.info("Scan alarm retry biz config, params is {}, alarmType is {}", JsonUtils.toJSONString(retryBizConfigDTOList), getEdiAlarmTypeEnum().getDesc());
        List<EdiAlarmInfo> ediAlarmInfoList = Lists.newArrayList();
        for (RetryBizConfigDTO retryBizConfigDTO : retryBizConfigDTOList) {
            RetryBizRecordCountQuery query = buildEdiAlarmQuery(retryBizConfigDTO);
            log.info("Scan alarm {} record query, params is {}", getEdiAlarmTypeEnum().getDesc(), JsonUtils.toJSONString(query));
            int count = retryBizRecordAdapterService.count(query);
            log.info("Scan alarm {} record query, matchCount is {}, alarmMaxCount is {}", getEdiAlarmTypeEnum().getDesc(), count, retryConfiguration.getAlarmMaxCount());
            if (count >= retryConfiguration.getAlarmMaxCount()) {
                ediAlarmInfoList.add(
                        EdiAlarmInfo.builder()
                                .total(count)
                                .bizName(retryBizConfigDTO.getBizName())
                                .bizType(retryBizConfigDTO.getBizType())
                                .ediAlarmTypeEnum(getEdiAlarmTypeEnum())
                                .title(getTitle())
                                .build()
                );
            }
            if (CollectionUtils.isEmpty(ediAlarmInfoList)) {
                return;
            }
            List<AlarmParam> alarmParamList = buildAlarmParamList(ediAlarmInfoList);
            batchSendNotice(alarmParamList);
        }
    }

    /**
     * 构建告警参数
     * @param ediAlarmInfoList
     * @return
     */
    protected List<AlarmParam> buildAlarmParamList(List<EdiAlarmInfo> ediAlarmInfoList) {
        List<AlarmParam> alarmParamList = Lists.newArrayList();
        long timestampSeconds = DateUtils.getCurrentSeconds();
        String sign = genSign(retryConfiguration.getAlarmSecret(), timestampSeconds);
        for (EdiAlarmInfo ediAlarmInfo : ediAlarmInfoList) {
            StringBuilder content = new StringBuilder()
                    .append("### [").append(env).append("] ").append(getTitle()).append("\n")
                    .append("**应用名**: ").append(applicationName).append("\n")
                    .append("**业务名称**: ").append(ediAlarmInfo.getBizName()).append("[").append(ediAlarmInfo.getBizType()).append("]").append("\n")
                    .append("**原因**: 共").append("[").append(ediAlarmInfo.getTotal()).append("]条一直处于").append("[").append(getEdiAlarmTypeEnum().getDesc()).append("]状态").append("\n")
                    .append("**告警时间**: ").append(DateUtils.getStrCurrentTime()).append("\n")
                    .append("<at user_id=\"all\">所有人</at> ").append("\n");
            TextContent textContent = TextContent.builder().text(content.toString()).build();
            GeneralFeishuBotAlarmMsg alarmMsg = GeneralFeishuBotAlarmMsg.builder()
                    .msg_type(FeishuMsgTypeEnum.TEXT.getType())
                    .sign(sign)
                    .timestamp(timestampSeconds)
                    .content(textContent)
                    .build();
            alarmParamList.add(AlarmParam.builder().webHook(retryConfiguration.getAlarmBoot()).content(alarmMsg).build());
        }
        return alarmParamList;
    }

    /**
     * 告警标题
     * @return
     */
    protected String getTitle() {
        EdiAlarmTypeEnum ediAlarmTypeEnum = getEdiAlarmTypeEnum();
        return String.format("[%s 告警 -- %s]", ediAlarmTypeEnum.getEdiTypeEnum().getDesc(), ediAlarmTypeEnum.getDesc());
    }

    /**
     * 构建查询条件
     * @param retryBizConfigDTO
     * @return
     */
    protected abstract RetryBizRecordCountQuery buildEdiAlarmQuery(RetryBizConfigDTO retryBizConfigDTO);

}
