package com.ah.cloud.permissions.biz.infrastructure.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggerContextVO;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.ah.cloud.permissions.biz.infrastructure.notice.feishu.ErrorLogAlarmService;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 日志告警
 * 技术选型
 * 针对日志监控，通常的手段有以下几种：
 * 1.在日志输出时，并在写到磁盘前，就可以通过拦截手段拿到日志并分析日志的级别，或者在异常处理时就触发日志警告(比如告警系统Cat),也可以通过Java agent的手段(如skywalking使用探针技术收集)，当然也可以在日志写到磁盘上，由ELK采集日志并分析日志，并将异常结果推送到用户，比如可以将日志采集并存储到阿里云的SLS，并由SLS分析并触发日志告警。
 * 2.Logback是由log4j创始人设计的又一个开源日志组件。logback当前分成三个模块：logback-core,logback- classic和logback-access。logback-core是其它两个模块的基础模块。logback-classic是log4j的一个 改良版本。此外logback-classic完整实现SLF4J API使你可以很方便地更换成其它日志系统如log4j或JDK14 Logging。logback-access访问模块与Servlet容器集成提供通过Http来访问日志的功能。
 * 三.为什么要使用Logback
 * 其他的告警，都需要搭建一套完整的告警系统，如Cat,skywalking等都需要搭建独立的系统。因此，我们就实现由logback集成企信机器人来实现告警功能。通过logback，我们是通过appender将日志分析并写入到磁盘上，那么我们可以在appender获取到日志内容，并判断是否需要发送告警推送。
 * 由于我们的系统中已经使用了Logback，我们就可以很方便的管理我们的日志。Logback也提供了一些可实现的日志处理供我们自定义实现来处理日志操作。
 *
 * 要注意的时候, logback的加载是在Spring bean加载之前的， 所以在Appender中是无法引用Spring Bean的
 * @author: YuKai Fan
 * @create: 2022-06-04 22:20
 **/
@Slf4j
public class AbstractAlarmAppender extends UnsynchronizedAppenderBase<LoggingEvent> {

    @Override
    protected void append(LoggingEvent loggingEvent) {
        try {
            Level level = loggingEvent.getLevel();
            // 只处理error级别的日志
            if (!Objects.equals(level, Level.ERROR)) {
                return;
            }
            LoggerContextVO loggerContextVO = loggingEvent.getLoggerContextVO();
            Map<String, String> propertyMap = loggerContextVO.getPropertyMap();
            String active = propertyMap.get("ACTIVE");
            String applicationName = propertyMap.get("APPLICATION_NAME");

            StringBuilder content = new StringBuilder()
                    .append("============= 错误日志告警 ================\n")
                    .append("应用名: ").append(applicationName)
                    .append("\n")
                    .append("当前环境: ").append(active)
                    .append("\n")
                    .append("日志等级: ").append(level.levelStr)
                    .append("\n")
                    .append("异常时间: ").append(DateUtils.getStrCurrentTime())
                    .append("\n")
                    .append("异常位置: ")
                    .append("\n")
                    .append("   1. 类名: ").append(loggingEvent.getLoggerName())
                    .append("\n")
                    .append("   2. 线程名: ").append(loggingEvent.getThreadName())
                    .append("\n")
                    .append("异常详情: ").append(loggingEvent.getFormattedMessage().length() <= 1800 ? loggingEvent.getFormattedMessage() : loggingEvent.getFormattedMessage().substring(0, 1800))
                    .append("\n")
                    .append("<at user_id=\"all\">所有人</at> ")
                    .append("\n");
            log.info("AbstractAlarmAppender[append] log alarm content is {}", content);
            ErrorLogAlarmService errorLogAlarmService = new ErrorLogAlarmService();
//            errorLogAlarmService.send(content.toString());
        } catch (Exception e) {
            addError("error log alarm error, reason is {}", e);
        }
    }
}
