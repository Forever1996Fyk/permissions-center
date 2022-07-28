package com.ah.cloud.permissions.biz.application.strategy.datascope;

import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 10:37
 **/
public interface DataScopeStrategy extends InitializingBean {

    /**
     * 获取数据权限拼接sql
     * @return
     */
    String getSql();
}
