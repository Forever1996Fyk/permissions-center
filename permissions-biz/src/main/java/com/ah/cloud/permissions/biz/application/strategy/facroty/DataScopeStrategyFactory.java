package com.ah.cloud.permissions.biz.application.strategy.facroty;

import com.ah.cloud.permissions.biz.application.strategy.datascope.DataScopeStrategy;
import com.ah.cloud.permissions.enums.DataScopeEnum;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 10:38
 **/
public class DataScopeStrategyFactory {

    private static Map<DataScopeEnum, DataScopeStrategy> FACTORY = Maps.newHashMap();

    public static void  register(DataScopeStrategy strategy, DataScopeEnum dataScopeEnum) {
        if (FACTORY.containsKey(dataScopeEnum)) {
            return;
        }
        FACTORY.put(dataScopeEnum, strategy);
    }


    public static DataScopeStrategy get(DataScopeEnum dataScopeEnum) {
        return FACTORY.get(dataScopeEnum);
    }
}
