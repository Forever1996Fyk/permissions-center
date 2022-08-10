package com.ah.cloud.permissions.edi.infrastructure.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 10:12
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "edi.retry")
public class RetryConfiguration {

    /**
     * retry 执行时间
     */
    private int runTime;

    /**
     * 切换次数
     */
    private int hourOverTimes;

    /**
     * 重试超时时间(毫秒)
     */
    private int retryTimout;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 真实表前后缀
     */
    private String retryRecordActualTableSuffix;


    /**
     * tech-edi 真实表前后缀
     */
    private String retryTechRecordActualTableSuffix;

    /**
     * edi真实表名
     */
    private String retryRecordActualTableName;

    /**
     * tech-edi真实表名
     */
    private String retryTechRecordActualTableName;

    /**
     * 告警最大数量
     */
    private Integer alarmMaxCount;

    /**
     * 告警机器人链接
     */
    private String alarmBoot;


    /**
     * 告警机器人链接
     */
    private String alarmSecret;

    /**
     * 告警初始时间
     */
    private Integer alarmMinute;



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
