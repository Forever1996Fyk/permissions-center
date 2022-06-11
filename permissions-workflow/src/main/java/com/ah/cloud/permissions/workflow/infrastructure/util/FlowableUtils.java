package com.ah.cloud.permissions.workflow.infrastructure.util;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 10:34
 **/
public class FlowableUtils {


    /**
     * 直接创建flow数据库
     *
     * @param args
     */
    public static void main(String[] args) {
        //1、创建ProcessEngineConfiguration实例,该实例可以配置与调整流程引擎的设置
        ProcessEngineConfiguration cfg=new StandaloneProcessEngineConfiguration()
                //2、通常采用xml配置文件创建ProcessEngineConfiguration，这里直接采用代码的方式
                //3、配置数据库相关参数
                .setJdbcUrl("jdbc:mysql://49.234.219.109:3306/workflow?serverTimezone=GMT%2B8&autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //4、初始化ProcessEngine流程引擎实例
        ProcessEngine processEngine=cfg.buildProcessEngine();
    }
}
