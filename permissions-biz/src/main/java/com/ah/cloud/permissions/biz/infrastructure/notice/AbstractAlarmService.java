package com.ah.cloud.permissions.biz.infrastructure.notice;

import com.ah.cloud.permissions.biz.domain.notice.alarm.AlarmParam;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.http.httpclient.HttpClientResult;
import com.ah.cloud.permissions.biz.infrastructure.http.httpclient.HttpClientUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 15:21
 **/
@Slf4j
public abstract class AbstractAlarmService implements AlarmService {

    @Override
    public void sendNotice(AlarmParam alarmParam) {
        // 参数校验
        String content = checkParamAndReturnContent(alarmParam);
        try {
            HttpClientResult httpClientResult = HttpClientUtils.doPost(alarmParam.getWebHook(), alarmParam.getExtraParams(), content);
            System.out.println(JsonUtils.toJSONString(httpClientResult));
        } catch (Exception e) {
            log.error("{}[sendNotice] send failed http client error, params is {}, reason is {}", getLogMark(), JsonUtils.toJSONString(alarmParam), Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.ALARM_PUSH_FAILED);
        }
    }

    /**
     * 获取日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 消息大小
     *
     * @return
     */
    protected abstract int maxSize();

    private String checkParamAndReturnContent(AlarmParam alarmParam) {
        if (StringUtils.isEmpty(alarmParam.getWebHook())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "webHook");
        }

        if (Objects.isNull(alarmParam.getContent())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "content");
        }
        String content = JsonUtils.toJSONString(alarmParam.getContent());
        if (content.length() > maxSize()) {
            throw new BizException(ErrorCodeEnum.ALARM_PUSH_FAILED_MSG_MAX_SIZE, String.valueOf(maxSize()));
        }
        return content;
    }

}
