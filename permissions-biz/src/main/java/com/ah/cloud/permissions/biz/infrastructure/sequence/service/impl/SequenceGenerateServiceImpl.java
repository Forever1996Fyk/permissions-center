package com.ah.cloud.permissions.biz.infrastructure.sequence.service.impl;

import com.ah.cloud.permissions.biz.infrastructure.sequence.DefaultSyncSequenceLoader;
import com.ah.cloud.permissions.biz.infrastructure.sequence.SequenceCache;
import com.ah.cloud.permissions.biz.infrastructure.sequence.model.BizSequence;
import com.ah.cloud.permissions.biz.infrastructure.sequence.repository.SequenceRepository;
import com.ah.cloud.permissions.biz.infrastructure.sequence.service.SequenceGenerateService;
import com.ah.cloud.permissions.biz.infrastructure.sequence.service.SequenceTypeService;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:12
 **/
@Slf4j
@Component
public class SequenceGenerateServiceImpl implements SequenceGenerateService, InitializingBean {
    @Resource
    private SequenceRepository sequenceRepository;
    @Resource
    private SequenceTypeService sequenceTypeService;
    /**
     * 序列缓存
     */
    private SequenceCache<String, Long> sequenceCache;

    /**
     * 队列阈值
     */
    @Value("${sequence_cache_size:100}")
    private int sequenceCacheSize;

    /**
     * 线程数
     */
    @Value("${sequence_thread_size:10}")
    private int sequenceThreadSize;

    /**
     * 线程数是否切换
     */
    @Value("${sequence_schedule_switch:false}")
    private boolean sequenceScheduleSwitch;


    @Override
    public BizSequence genBizId(String bizType) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> typeConfig = sequenceTypeService.getSequenceTypeConfig();
        if (CollectionUtils.isEmpty(typeConfig)) {
            log.warn("SequenceGenerateServiceImpl run load Sequence Type Config is empty");
            return;
        }
        if (sequenceCache == null) {
            sequenceCache = new SequenceCache<>(
                    typeConfig,
                    new DefaultSyncSequenceLoader(sequenceRepository),
                    null
            );
        }
    }


}
