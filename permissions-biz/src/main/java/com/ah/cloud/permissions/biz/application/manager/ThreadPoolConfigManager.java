package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.domain.cfg.threadpool.dto.CfgThreadPoolDTO;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.ThreadPoolDataVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.CfgThreadPool;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 线程池配置管理
 * @author: YuKai Fan
 * @create: 2022-04-26 08:03
 **/
@Slf4j
@Component
public class ThreadPoolConfigManager {

    /**
     * 动态设置线程池参数
     * @param cfgThreadPool
     */
    public void reConfigureThreadPool(CfgThreadPool cfgThreadPool) {

    }

    /**
     * 根据线程池名采集监控数据
     * @param name
     * @return
     */
    public ThreadPoolDataVo collectThreadPoolMonitorData(String name) {
        return ThreadPoolDataVo.builder().build();
    }

    /**
     * 采集线程池配置信息
     * @param name
     * @return
     */
    public CfgThreadPoolDTO collectThreadPoolConfig(String name) {
        return CfgThreadPoolDTO.builder().build();
    }

    /**
     * 采集线程池配置列表
     * @return
     */
    public List<CfgThreadPoolDTO> collectThreadPoolConfigList() {
        return Lists.newArrayList();
    }
}
