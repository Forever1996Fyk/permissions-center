package com.ah.cloud.permissions.edi.infrastructure.shard.algorithm;

import com.ah.cloud.permissions.biz.infrastructure.util.SpringUtils;
import com.ah.cloud.permissions.edi.infrastructure.config.RetryConfiguration;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Objects;

import static com.ah.cloud.permissions.edi.infrastructure.constant.EdiConstants.SEPARATOR_UNDERLINE;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-25 21:34
 **/
@Slf4j
public class TechBizRetryShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private RetryConfiguration retryConfiguration;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> shardingValue) {
        try {
            if (Objects.isNull(retryConfiguration)) {
                retryConfiguration = SpringUtils.getBean(RetryConfiguration.class);
            }
            String[] keys = shardingValue.getValue().split(SEPARATOR_UNDERLINE);
            int tableSuffix = Integer.parseInt(keys[1]) % retryConfiguration.getRetryTechRecordActualTableSuffixList().size();
            return retryConfiguration.getRetryTechRecordActualTableName() + SEPARATOR_UNDERLINE + keys[0] + SEPARATOR_UNDERLINE + tableSuffix;

        } catch (Exception e) {
            log.error("TechBizRetryShardingAlgorithm[doSharding] error, shardingValue is {}, reason is {}", shardingValue.getValue(), Throwables.getStackTraceAsString(e));
            throw e;
        }
    }
}
