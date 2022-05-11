package com.ah.cloud.permissions.biz.infrastructure.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 14:40
 **/
public class JsonLayoutEncoder extends LayoutWrappingEncoder<ILoggingEvent> {

    public JsonLayoutEncoder() {
    }

    @Override
    public void start() {
        CustomerJsonLayout jsonLayout = new CustomerJsonLayout();
        jsonLayout.setContext(this.context);
        jsonLayout.setIncludeContextName(false);
        jsonLayout.setAppendLineSeparator(true);
        jsonLayout.setJsonFormatter(new JacksonJsonFormatter());
        jsonLayout.start();
        super.setCharset(StandardCharsets.UTF_8);
        super.setLayout(jsonLayout);
        super.start();
    }
}
