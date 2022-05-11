package com.ah.cloud.permissions.biz.infrastructure.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import io.lettuce.core.tracing.TraceContext;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 14:41
 **/
public class CustomerJsonLayout extends JsonLayout {
    public CustomerJsonLayout() {
    }

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put("timestamp", Instant.ofEpochMilli(event.getTimeStamp()).atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        map.put("app_name", this.context.getProperty("appName"));
//        map.put("user_id", Objects.toString(Constant.CTX_KEY_USER_ID.get(), ""));
//        map.put("client_id", Objects.toString(Constant.CTX_KEY_CLIENT_ID.get(), ""));
//        map.put("client_ip", Objects.toString(Constant.CTX_KEY_CLIENT_IP.get(), ""));
//        map.put("trace_id", TraceContext.traceId());
    }
}
