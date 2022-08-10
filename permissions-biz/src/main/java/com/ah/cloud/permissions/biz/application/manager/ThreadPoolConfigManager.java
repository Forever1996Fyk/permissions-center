package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.ThreadPoolConfigChecker;
import com.ah.cloud.permissions.biz.application.helper.ThreadPoolConfigHelper;
import com.ah.cloud.permissions.biz.application.service.CfgThreadPoolService;
import com.ah.cloud.permissions.biz.application.service.ThreadPoolDataService;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.dto.CfgThreadPoolDTO;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.dto.CollectThreadPoolCfgDTO;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolAddForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolUpdateForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.query.CfgThreadPoolQuery;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.query.ThreadPoolDataQuery;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.CfgThreadPoolVo;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.ThreadPoolDataVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.CfgThreadPool;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ThreadPoolData;
import com.ah.cloud.permissions.biz.infrastructure.threadpool.ResizeLinkedBlockingQueue;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.BlockingQueueEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 线程池配置管理
 * @author: YuKai Fan
 * @create: 2022-04-26 08:03
 **/
@Slf4j
@Component
public class ThreadPoolConfigManager implements InitializingBean {
    @Resource
    private CfgThreadPoolService cfgThreadPoolService;
    @Resource
    private ThreadPoolDataService threadPoolDataService;
    @Resource
    private ThreadPoolConfigHelper threadPoolConfigHelper;
    @Resource
    private ThreadPoolConfigChecker threadPoolConfigChecker;

    /**
     * 动态设置线程池参数
     *
     * @param cfgThreadPool
     */
    public void reConfigureThreadPool(CfgThreadPoolDTO cfgThreadPool) {
        Integer corePoolSize = cfgThreadPool.getCorePoolSize();
        Integer maximumPoolSize = cfgThreadPool.getMaximumPoolSize();
        Integer workQueueSize = cfgThreadPool.getWorkQueueSize();
        String name = cfgThreadPool.getName();
        Class<ThreadPoolManager> clazz = ThreadPoolManager.class;
        ThreadPoolExecutor executor;
        try {
            Field field = clazz.getField(name);
            executor = (ThreadPoolExecutor) field.get(clazz);
        } catch (NoSuchFieldException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_IS_NULL, name);
        } catch (IllegalAccessException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_GET_FAILED, name);
        }

        BlockingQueue<Runnable> queue = executor.getQueue();
        if (queue instanceof ResizeLinkedBlockingQueue) {
            ResizeLinkedBlockingQueue resizeQueue = (ResizeLinkedBlockingQueue) queue;
            int oldWorkQueueSize = resizeQueue.size() + resizeQueue.getCapacity();
            if (!Objects.equals(workQueueSize, oldWorkQueueSize)) {
                resizeQueue.setCapacity(workQueueSize);
            }
        }
        int oldCorePoolSize = executor.getCorePoolSize();
        int oldMaximumPoolSize = executor.getMaximumPoolSize();

        if (!Objects.equals(corePoolSize, oldCorePoolSize)) {
            executor.setCorePoolSize(corePoolSize);
        }

        if (!Objects.equals(maximumPoolSize, oldMaximumPoolSize)) {
            executor.setMaximumPoolSize(maximumPoolSize);
        }
    }

    /**
     * 根据线程池名采集监控数据
     *
     * @param name 线程池名称
     * @return
     */
    public ThreadPoolDataVo collectThreadPoolMonitorData(String name) {
        Class<ThreadPoolManager> clazz = ThreadPoolManager.class;
        ThreadPoolExecutor executor;
        try {
            Field field = clazz.getField(name);
            executor = (ThreadPoolExecutor) field.get(clazz);
        } catch (NoSuchFieldException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_IS_NULL, name);
        } catch (IllegalAccessException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_GET_FAILED, name);
        }
        BlockingQueue<Runnable> queue = executor.getQueue();
        return ThreadPoolDataVo.builder()
                .name(name)
                .client(getClient())
                .corePoolSize(executor.getCorePoolSize())
                .maximumPoolSize(executor.getMaximumPoolSize())
                .activeCount(executor.getActiveCount())
                .poolSize(executor.getPoolSize())
                .largestPoolSize(executor.getLargestPoolSize())
                .taskCount(executor.getTaskCount())
                .completedTaskCount(executor.getCompletedTaskCount())
                .queueCapacity(queue.size() + queue.remainingCapacity())
                .queueSize(queue.size())
                .queueRemainingCapacity(queue.remainingCapacity())
                .build();
    }

    /**
     * 采集线程池配置信息
     *
     * @param name
     * @return
     */
    public CollectThreadPoolCfgDTO collectThreadPoolConfig(String name) {
        Class<ThreadPoolManager> clazz = ThreadPoolManager.class;
        ThreadPoolExecutor executor;
        try {
            Field field = clazz.getField(name);
            executor = (ThreadPoolExecutor) field.get(clazz);
        } catch (NoSuchFieldException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_IS_NULL, name);
        } catch (IllegalAccessException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_GET_FAILED, name);
        }
        BlockingQueue<Runnable> queue = executor.getQueue();
        return CollectThreadPoolCfgDTO.builder()
                .name(name)
                .corePoolSize(executor.getCorePoolSize())
                .maximumPoolSize(executor.getMaximumPoolSize())
                .keepAliveTime(executor.getKeepAliveTime(TimeUnit.SECONDS))
                .queueType(BlockingQueueEnum.RESIZE_LINKED_BLOCKING_QUEUE.getValue())
                .workQueueSize(queue.size() + queue.remainingCapacity())
                .build();
    }

    /**
     * 采集线程池配置列表
     *
     * @return
     */
    public List<CollectThreadPoolCfgDTO> collectThreadPoolConfigList() {
        Class<ThreadPoolManager> clazz = ThreadPoolManager.class;
        Field[] fields = clazz.getFields();
        List<CollectThreadPoolCfgDTO> collectThreadPoolCfgDTOList = Lists.newArrayList();
        for (Field field : fields) {
            String name = field.getName();
            CollectThreadPoolCfgDTO collectThreadPoolCfgDTO = this.collectThreadPoolConfig(name);
            if (Objects.nonNull(collectThreadPoolCfgDTO)) {
                collectThreadPoolCfgDTOList.add(collectThreadPoolCfgDTO);
            }
        }
        return collectThreadPoolCfgDTOList;
    }

    private String getClient() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_GET_CLIENT_FAILED, "获取客户端地址失败");
        }
    }

    /**
     * 项目启动时, 初始化数据库中的线程池配置, 如果存在就加载配置到ThreadPoolManager类的每个线程池
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Class<ThreadPoolManager> clazz = ThreadPoolManager.class;
        Field[] fields = clazz.getFields();
        Set<String> fieldNameSet = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());
        List<CfgThreadPool> cfgThreadPools = cfgThreadPoolService.list(
                new QueryWrapper<CfgThreadPool>().lambda()
                        .in(CfgThreadPool::getName, fieldNameSet)
                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
        );

        //若数据库中配置了该线程池，则使用数据库中配置的参数重新设置线程池参数
        cfgThreadPools.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    log.info("使用数据库中的线程池配置参数重新设置线程池,线程池名称:{}", item.getName());
                    this.reConfigureThreadPool(threadPoolConfigHelper.convertToDTO(item));
                });
    }

    /**
     * 添加线程池配置
     *
     * @param form
     */
    public void addThreadPoolConfig(CfgThreadPoolAddForm form) {
        threadPoolConfigChecker.checkBaseConfig(form);
        CfgThreadPool cfgThreadPool = threadPoolConfigHelper.convertToEntity(form);
        cfgThreadPoolService.save(cfgThreadPool);
    }

    /**
     * 更新线程池配置
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateThreadPoolConfig(CfgThreadPoolUpdateForm form) {
        threadPoolConfigChecker.checkBaseConfig(form);
        CfgThreadPool existCfgThreadPool = cfgThreadPoolService.getOne(
                new QueryWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getId, form.getId())
                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existCfgThreadPool)) {
            throw new BizException(ErrorCodeEnum.CFG_THREAD_POOL_NOT_EXIST, String.valueOf(form.getId()));
        }

        if (existCfgThreadPool.getWorkQueueSize().compareTo(form.getWorkQueueSize()) < 0) {
            throw new BizException(ErrorCodeEnum.CFG_THREAD_POOL_WORK_QUEUE_SIZE_ERROR, String.valueOf(existCfgThreadPool.getWorkQueueSize()));
        }

        CfgThreadPool cfgThreadPool = threadPoolConfigHelper.convertToEntity(form);
        cfgThreadPool.setVersion(existCfgThreadPool.getVersion());
        boolean updateResult = cfgThreadPoolService.update(
                cfgThreadPool,
                new UpdateWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getId, existCfgThreadPool.getId())
                        .eq(CfgThreadPool::getVersion, existCfgThreadPool.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }

        reConfigureThreadPool(threadPoolConfigHelper.convertToDTO(cfgThreadPool));
    }

    /**
     * 根据id查询线程池配置
     *
     * @param id
     * @return
     */
    public CfgThreadPoolVo findById(Long id) {
        CfgThreadPool cfgThreadPool = cfgThreadPoolService.getOne(
                new QueryWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getId, id)
                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(cfgThreadPool)) {
            throw new BizException(ErrorCodeEnum.CFG_THREAD_POOL_NOT_EXIST, String.valueOf(id));
        }
        return threadPoolConfigHelper.convertToVo(cfgThreadPool);
    }

    /**
     * 根据id删除线程池配置
     *
     * @param id
     */
    public void deleteById(Long id) {
        CfgThreadPool cfgThreadPool = cfgThreadPoolService.getOne(
                new QueryWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getId, id)
                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(cfgThreadPool)) {
            throw new BizException(ErrorCodeEnum.CFG_THREAD_POOL_NOT_EXIST, String.valueOf(id));
        }

        CfgThreadPool updateCfgThreadPool = new CfgThreadPool();
        updateCfgThreadPool.setId(cfgThreadPool.getId());
        updateCfgThreadPool.setDeleted(cfgThreadPool.getId());
        updateCfgThreadPool.setVersion(cfgThreadPool.getVersion());

        boolean deleteResult = cfgThreadPoolService.update(
                updateCfgThreadPool,
                new UpdateWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getId, id)
                        .eq(CfgThreadPool::getVersion, cfgThreadPool.getVersion())
        );
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 分页查询线程池配置
     *
     * @param query
     * @return
     */
    public PageResult<CfgThreadPoolVo> pageCfgThreadPoolList(CfgThreadPoolQuery query) {
        PageInfo<CfgThreadPool> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> cfgThreadPoolService.list(
                                new QueryWrapper<CfgThreadPool>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getName()),
                                                CfgThreadPool::getName, query.getName()
                                        )
                                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
                        ));

        return threadPoolConfigHelper.convertToPageResult(pageInfo);
    }

    /**
     * 分页查询历史线程监控数据
     *
     * @param query
     * @return
     */
    public PageResult<ThreadPoolDataVo> pageHistoryMonitorData(ThreadPoolDataQuery query) {
        Integer shardingKey = threadPoolConfigChecker.checkThreadPoolDataQueryAndReturnDate(query);
        PageInfo<ThreadPoolData> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> threadPoolDataService.list(
                                new QueryWrapper<ThreadPoolData>().lambda()
                                        .eq(ThreadPoolData::getShardingKey, shardingKey)
                                        .eq(
                                                StringUtils.isNotEmpty(query.getName()),
                                                ThreadPoolData::getName, query.getName()
                                        )
                                        .eq(
                                                StringUtils.isNotEmpty(query.getClient()),
                                                ThreadPoolData::getClient, query.getClient()
                                        )
                                        .ge(
                                                StringUtils.isNotEmpty(query.getStartTime()),
                                                ThreadPoolData::getCreateTime, query.getStartTime()
                                        )
                                        .le(
                                                StringUtils.isNotEmpty(query.getEndTime()),
                                                ThreadPoolData::getCreateTime, query.getEndTime()
                                        )
                                        .eq(ThreadPoolData::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return threadPoolConfigHelper.convertToThreadDataPageResult(pageInfo);
    }

    /**
     * 根据线程池名称查询实时监控数据
     *
     * @param name
     * @return
     */
    public ThreadPoolDataVo findRealTimeMonitorData(String name) {
        return this.collectThreadPoolMonitorData(name);
    }

    /**
     * 更新线程池监控数据 todo 定时任务调度
     */
    public void uploadThreadPoolMonitorData() {
        List<CfgThreadPool> cfgThreadPoolList = cfgThreadPoolService.list(
                new QueryWrapper<CfgThreadPool>().lambda()
                        .eq(CfgThreadPool::getDeleted, DeletedEnum.NO.value)
        );
        if(CollectionUtils.isEmpty(cfgThreadPoolList)){
            log.info("没有找到线程池配置，不需要上报监控数据");
            return;
        }
        for (CfgThreadPool cfgThreadPool : cfgThreadPoolList) {
            ThreadPoolDataVo threadPoolDataVo = this.collectThreadPoolMonitorData(cfgThreadPool.getName());
            threadPoolDataService.save(threadPoolConfigHelper.convertToEntity(threadPoolDataVo));
        }
    }
}
