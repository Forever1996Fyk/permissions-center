package com.ah.cloud.permissions.edi.application.manager;

import com.ah.cloud.permissions.edi.domain.record.table.RetryBizRecordTable;
import com.ah.cloud.permissions.edi.infrastructure.config.RetryConfiguration;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 11:13
 **/
@Slf4j
@Component
public class RetryResourceManager {
    @Resource
    private RetryConfiguration retryConfiguration;


    /**
     * 重试记录逻辑表名
     */
    public static final String DEFAULT_RETRY_RECORD_LOGIC_TABLE_NAME = "edi_cfg_biz_retry";
    public static final String DEFAULT_RETRY_RECORD_TASK_LOGIC_TABLE_NAME = "retry_biz_record_task";
    /**
     * 重试配置逻辑表名
     */
    public static final String DEFAULT_RETRY_CONFIG_LOGIC_TABLE_NAME = "edi_cfg_biz_retry";
    /**
     * Tech重试记录逻辑表名
     */
    public static final String DEFAULT_RETRY_TECH_RECORD_LOGIC_TABLE_NAME = "edi_tech_biz_retry_record";
    public static final String DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME = "retry_tech_biz_record_task";

    /**
     * Tech重试配置逻辑表名
     */
    public static final String DEFAULT_RETRY_TECH_CONFIG_LOGIC_TABLE_NAME = "edi_cfg_tech_biz_retry";

    /**
     * 重试记录逻辑表名
     */
    private static final String RETRY_RECORD_TASK_LOGIC_TABLE_NAME = DEFAULT_RETRY_RECORD_TASK_LOGIC_TABLE_NAME;

    /**
     * Tech重试记录逻辑表名
     */
    private static final String RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME = DEFAULT_RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME;

    /**
     * 获取真实的edi record表名
     *
     * @param dateMonth
     * @param retryRecordActualTableName
     * @param actualTableSuffix
     * @return
     */
    private String getActualBizRecordTable(String dateMonth, String retryRecordActualTableName, String actualTableSuffix) {
        return retryRecordActualTableName.concat("_").concat(dateMonth).concat("_").concat(actualTableSuffix);
    }

    /**
     * 构建record 分表数据
     * @param dateMonth
     * @param index
     * @param total
     * @param ediTypeEnum
     * @return
     */
    public List<RetryBizRecordTable> buildRetryRecordActualTableSliceList(String dateMonth, int index, int total, EdiTypeEnum ediTypeEnum) {
        List<RetryBizRecordTable> retryBizRecordTableList = Lists.newArrayList();
        List<String> retryRecordActualTableSuffixList;
        String actualTableName;
        String logicTableName;
        if (ediTypeEnum.isTech()) {
            logicTableName = RETRY_TECH_RECORD_TASK_LOGIC_TABLE_NAME;
            actualTableName = retryConfiguration.getRetryTechRecordActualTableName();
            retryRecordActualTableSuffixList = retryConfiguration.getRetryTechRecordActualTableSuffixList();
        } else {
            logicTableName = RETRY_RECORD_TASK_LOGIC_TABLE_NAME;
            actualTableName = retryConfiguration.getRetryRecordActualTableName();
            retryRecordActualTableSuffixList = retryConfiguration.getRetryRecordActualTableSuffixList();
        }
        int tableIndex = 0;
        List<String> dataSourceList = retryConfiguration.getDataSourceList();
        for (String dataSource : dataSourceList) {
            for (String actualTableSuffix : retryRecordActualTableSuffixList) {
                if (tableIndex++ % total == index) {
                    String actualTable = getActualBizRecordTable(dateMonth, actualTableName, actualTableSuffix);
                    retryBizRecordTableList.add(
                            RetryBizRecordTable.builder().dataSource(dataSource).actualTable(actualTable).logicTable(logicTableName).build()
                    );
                }
            }
        }
        return retryBizRecordTableList;
    }

}
