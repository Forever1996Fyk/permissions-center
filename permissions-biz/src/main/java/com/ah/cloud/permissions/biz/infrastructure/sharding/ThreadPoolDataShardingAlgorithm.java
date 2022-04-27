package com.ah.cloud.permissions.biz.infrastructure.sharding;

import com.ah.cloud.permissions.biz.infrastructure.constant.ShardingConstants;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-27 17:20
 **/
public class ThreadPoolDataShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        String loginTableName = preciseShardingValue.getLogicTableName();
        Integer shardingKey = preciseShardingValue.getValue();
        return loginTableName+ ShardingConstants.TABLE_SEPARATOR +shardingKey;
    }
}
