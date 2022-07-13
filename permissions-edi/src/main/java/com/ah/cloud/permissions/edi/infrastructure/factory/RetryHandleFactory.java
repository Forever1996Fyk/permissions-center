package com.ah.cloud.permissions.edi.infrastructure.factory;

import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizTypeDTO;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryHandle;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: permissions-center
 * @description:   重试处理器工厂
 *   这里必须实现ApplicationContextAware 从Spring IOC工厂ApplicationContext中获取当前RetryHandle 的bean, 否则直接从map中获取的处理器实体没有事务管理
 *
 * @author: YuKai Fan
 * @create: 2022-07-06 11:42
 **/
@Slf4j
@Component
public class RetryHandleFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * bizType, RetryHandle 缓存器
     */
    private static final Map<Integer, RetryHandle> RETRY_HANDLE_MAP = Maps.newHashMap();

    /**
     * bizType, RetryBizType 缓存器
     */
    private static final Map<Integer, RetryBizTypeDTO> RETRY_BIZ_TYPE_MAP = Maps.newHashMap();

    /**
     * tech bizType, RetryHandle 缓存器
     */
    private static final Map<Integer, RetryHandle> TECH_RETRY_HANDLE_MAP = Maps.newHashMap();

    /**
     * tech bizType, RetryBizType 缓存器
     */
    private static final Map<Integer, RetryBizTypeDTO> TECH_RETRY_BIZ_TYPE_MAP = Maps.newHashMap();


    /**
     * 初始化锁
     */
    private static final Lock LOCK = new ReentrantLock();

    /**
     * 注册处理器
     *
     * @param bizType
     * @param retryHandle
     */
    public static void registerRetryHandle(RetryBizTypeDTO bizType, RetryHandle retryHandle) {
        LOCK.lock();
        try {
            if (RETRY_HANDLE_MAP.containsKey(bizType.getType())) {
                throw new EdiException(EdiErrorCodeEnum.RETRY_HANDLE_REPEAT);
            }
            RETRY_HANDLE_MAP.put(bizType.getType(), retryHandle);
            RETRY_BIZ_TYPE_MAP.put(bizType.getType(), bizType);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 注册处理器
     *
     * @param bizType
     * @param retryHandle
     */
    public static void registerTechRetryHandle(RetryBizTypeDTO bizType, RetryHandle retryHandle) {
        LOCK.lock();
        try {
            if (TECH_RETRY_HANDLE_MAP.containsKey(bizType.getType())) {
                throw new EdiException(EdiErrorCodeEnum.RETRY_HANDLE_REPEAT);
            }
            TECH_RETRY_HANDLE_MAP.put(bizType.getType(), retryHandle);
            TECH_RETRY_BIZ_TYPE_MAP.put(bizType.getType(), bizType);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 获取重试处理器
     *
     * @param bizType
     * @param ediTypeEnum
     * @return
     */
    public static RetryHandle getRetryHandle(Integer bizType, EdiTypeEnum ediTypeEnum) {
        RetryHandle retryHandle;
        if (ediTypeEnum.isTech()) {
            retryHandle = TECH_RETRY_HANDLE_MAP.get(bizType);
        } else {
            retryHandle = RETRY_HANDLE_MAP.get(bizType);
        }
        if (Objects.isNull(retryHandle)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_HANDLER_IS_NULL);
        }
        return context.getBean(retryHandle.getClass());
    }

    /**
     * 获取重试业务类型
     * @param bizType
     * @param ediTypeEnum
     * @return
     */
    public static RetryBizTypeDTO getRetryBizType(Integer bizType, EdiTypeEnum ediTypeEnum) {
        RetryBizTypeDTO retryBizTypeDTO;
        if (ediTypeEnum.isTech()) {
            retryBizTypeDTO = TECH_RETRY_BIZ_TYPE_MAP.get(bizType);
        } else {
            retryBizTypeDTO = RETRY_BIZ_TYPE_MAP.get(bizType);
        }
        if (Objects.isNull(retryBizTypeDTO)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_TYPE_IS_NULL);
        }
        return retryBizTypeDTO;
    }

    /**
     * 获取业务类型集合
     * @param ediTypeEnum
     * @return
     */
    public static List<RetryBizTypeDTO> listRetryBizType(EdiTypeEnum ediTypeEnum) {
        List<RetryBizTypeDTO> retryBizTypeList;
        if (ediTypeEnum.isTech()) {
            retryBizTypeList = Lists.newArrayList(TECH_RETRY_BIZ_TYPE_MAP.values());
        } else {
            retryBizTypeList = Lists.newArrayList(RETRY_BIZ_TYPE_MAP.values());
        }
        retryBizTypeList.sort(Comparator.comparing(RetryBizTypeDTO::getType));
        return retryBizTypeList;
    }

    /**
     * 包一个静态方法, 解决sonar代码检查
     * @param applicationContext
     */
    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }
}
