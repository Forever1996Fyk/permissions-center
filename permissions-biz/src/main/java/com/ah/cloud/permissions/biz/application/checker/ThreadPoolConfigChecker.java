package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolBaseForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.query.ThreadPoolDataQuery;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-27 15:00
 **/
@Component
public class ThreadPoolConfigChecker {

    public void checkBaseConfig(CfgThreadPoolBaseForm form) {
        if (form.getCorePoolSize().compareTo(form.getMaximumPoolSize()) > 0) {
            throw new BizException(ErrorCodeEnum.THREAD_POOL_CORE_SIZE_MORE_THAN_MAX_SIZE);
        }

    }

    /**
     * 校验线程池数据查询参数
     *
     * 因为线程池分表, 所以开始时间，结束时间不能跨月
     * @param query
     */
    public Integer checkThreadPoolDataQueryAndReturnDate(ThreadPoolDataQuery query) {
        Integer date = DateUtils.checkDateTimeIsAcrossMonthAndReturnDate(query.getStartTime(), query.getEndTime());
        if(Objects.isNull(date)){
            throw new BizException(ErrorCodeEnum.DATE_CANNOT_ACROSS_THE_MONTH);
        }
        return date;
    }
}
