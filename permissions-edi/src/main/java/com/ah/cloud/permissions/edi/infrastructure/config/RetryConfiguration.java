package com.ah.cloud.permissions.edi.infrastructure.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 10:12
 **/
@Data
@Component
@ConfigurationProperties(prefix = "edi.retry")
public class RetryConfiguration {

    /**
     * retry 执行时间
     */
    @Value(value = "${runTime:1000}")
    private int runTime;

    /**
     * 切换次数
     */
    @Value(value = "${hourOverTimes:10}")
    private int hourOverTimes;

    /**
     * 重试超时时间(毫秒)
     */
    @Value(value = "${retryTimout:2000}")
    private int retryTimout;

    /**
     * 数据源
     */
    @Value(value = "${datasource}")
    private String dataSource;

    /**
     * 真实表前后缀
     */
    @Value(value = "${retryRecordActualTableSuffix}")
    private String retryRecordActualTableSuffix;


    /**
     * tech-edi 真实表前后缀
     */
    @Value(value = "${retryTechRecordActualTableSuffix}")
    private String retryTechRecordActualTableSuffix;

    /**
     * edi真实表名
     */
    @Value(value = "${retryRecordActualTableName}")
    private String retryRecordActualTableName;

    /**
     * tech-edi真实表名
     */
    @Value(value = "${retryTechRecordActualTableName}")
    private String retryTechRecordActualTableName;

    /**
     * 告警最大数量
     */
    @Value(value = "${alarm.maxCount:30}")
    private Integer alarmMaxCount;

    /**
     * 告警机器人链接
     */
    @Value(value = "${alarm.boot}")
    private String alarmBoot;


    /**
     * 告警机器人链接
     */
    @Value(value = "${alarm.secret}")
    private String secret;

    /**
     * 告警初始时间
     */
    @Value(value = "${alarm.minute:30}")
    private Integer alarmInitMinute;



    public List<String> getDataSourceList() {
        return Arrays.asList(dataSource.split(","));
    }

    public List<String> getRetryRecordActualTableSuffixList() {
        return getRetryTableSuffixList(retryRecordActualTableSuffix);
    }

    public List<String> getRetryTechRecordActualTableSuffixList() {
        return getRetryTableSuffixList(retryTechRecordActualTableSuffix);
    }

    private List<String> getRetryTableSuffixList(String tableSuffixConfig) {
        List<String> tableSuffix = Lists.newArrayList(tableSuffixConfig.split(","));
        /*
        这里的逻辑是, 如果分表的配置如下:
        0, ..., 99
        则表示edi分表是0-99, 也就是100张表， 所以从最后一个suffix开始循环计算
         */
        if (tableSuffix.size() == 3 && tableSuffix.get(1).equals("...")) {
            int startIndex = Integer.parseInt(tableSuffix.get(0));
            int endIndex = Integer.parseInt(tableSuffix.get(2));
            tableSuffix.clear();
            for (int i = startIndex; i <= endIndex; i++) {
                tableSuffix.add(String.valueOf(i));
            }
        }
        return tableSuffix;
    }

}
