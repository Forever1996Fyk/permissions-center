package com.ah.cloud.permissions.workflow.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.EngineConfigurator;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: permissions-center
 * @description: 更换数据源
 *               在多数据源的项目中, 如果不想把flowable的表放在默认数据源下, 就需要用到下面配置
 *
 *               flowable利用SPI机制默认加载原数据源, 所以需要用SPI来更换数据源
 * @author: YuKai Fan
 * @create: 2022-07-05 09:44
 **/
@Slf4j
public class ChangeDbEngineConfigurator implements EngineConfigurator {

    /**
     * 判断是否是第一次加载标识
     */
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean();

    /**
     * 默认工作流数据源key
     */
    private final static String DEFAULT_WORKFLOW_DATASOURCE_KEY = "workflow0";

    @Override
    public void beforeInit(AbstractEngineConfiguration engineConfiguration) {
        if (INITIALIZED.compareAndSet(false, true)) {
            DataSource dataSource = engineConfiguration.getDataSource();
            /*
                由于项目用到了sharding-jdbc, 所以这里只判断sharding-jdbc数据源
                如果有需求可以再加if-else
             */
            if (dataSource instanceof TransactionAwareDataSourceProxy) {
                log.info("ChangeDbEngineConfigurator[beforeInit] change flowable datasource to workflow");
                TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = (TransactionAwareDataSourceProxy) dataSource;
                DataSource targetDataSource = transactionAwareDataSourceProxy.getTargetDataSource();
                if (targetDataSource instanceof ShardingDataSource) {
                    ShardingDataSource shardingDataSource  = (ShardingDataSource) targetDataSource;
                    Map<String, DataSource> dataSourceMap = shardingDataSource.getDataSourceMap();
                    DataSource workflowDataSource = dataSourceMap.get(DEFAULT_WORKFLOW_DATASOURCE_KEY);
                    engineConfiguration.setDataSource(workflowDataSource);
                }
            }
        }
    }

    @Override
    public void configure(AbstractEngineConfiguration engineConfiguration) {

    }

    @Override
    public int getPriority() {
        return 0;
    }
}
