package com.ah.cloud.permissions.edi.domain.record.model;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.infrastructure.constant.EdiConstants;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.BizSuccessException;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryHandle;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 18:15
 **/
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecord {

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务单据号
     */
    private String bizNo;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 系统来源
     */
    private String bizSource;

    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 上次执行时间
     */
    private Date lastOpTime;

    /**
     * 失败 成功 重试中
     */
    private BizRetryStatusEnum recordStatus;

    /**
     * 执行参数
     */
    private String bizParams;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 扩展信息
     */
    private String ext;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 分片key
     */
    private String shardingKey;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 环境标字段
     */
    private String env;

    /**
     * edi重试配置
     */
    private RetryBizConfigCacheDTO retryBizConfig;

    /**
     * 重试处理器
     */
    private RetryHandle retryHandle;

    public void doFilter() {
        // 重试配置是否可用
        if (retryBizConfig.getStatus().equals(YesOrNoEnum.NO.getType())) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_RECORD_CONFIG_IS_STOP);
        }
        // 不可重试类型
        if (isCannotRetry()) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_RECORD_CONFIG_IS_CANNOT_RETRY);
        }
        // 达到最大次数
        if (isMaxRetryTimes()) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_RECORD_RETRY_OVER_LIMIT);
        }
        // 如果是手动重试 则不继续下面的判断
        if (retryBizConfig.getRetryModel().isHand()) {
            return;
        }
        // 如果当前状态为初始, 则直接执行
        if (Objects.equals(recordStatus, BizRetryStatusEnum.RETRY_INIT)) {
            return;
        }
        // 最近执行时间如果为空, 则表示第一次执行, 也直接放行
        if (Objects.isNull(lastOpTime)) {
            return;
        }
        filterLastOpTime();
    }

    /**
     * @description 是否不可重试
     *
     * 当前业务类型的重试配置, 重试最大次数为0, 表示不能重试
     */
    public boolean isCannotRetry() {
        return retryBizConfig.getMaxRetryTimes() == 0;
    }

    /**
     * @description 是否达到最大重试次数
     */
    public boolean isMaxRetryTimes() {
        return retryBizConfig.getMaxRetryTimes() > 0 && this.retryTimes >= retryBizConfig.getMaxRetryTimes();
    }

    /**
     * 过滤最近操作时间
     *
     * 根据重试配置的 执行间隔参数判断
     *
     * 如果最近操作时间+执行间隔, 没有达到当前时间, 则直接返回
     */
    public void filterLastOpTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastOpTime);
        calendar.add(Calendar.MILLISECOND, retryBizConfig.getRetryInterval());
        Date nextOpTime = calendar.getTime();
        if (DateUtils.beforeDate(nextOpTime)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_RECORD_NOT_ARRIVE_TIME);
        }
    }

    /**
     * 重试执行
     */
    public void doRetry() {
        BizRetryStatusEnum statusEnum = BizRetryStatusEnum.RETRY_SUCCESS;
        try {
            this.retryHandle.doRetry(this.getBizParams());
        }  catch (BizSuccessException bizSuccessException) {
            log.warn("RetryBizRecord[doRetry] doRetry failed with bizSuccessException, reason is {}, params is {}", Throwables.getStackTraceAsString(bizSuccessException), this);
            this.setErrorMessage(bizSuccessException.getErrorMessage());
        } catch (Throwable throwable) {
            log.error("RetryBizRecord[doRetry] doRetry failed with throwable, reason is {}, params is {}", Throwables.getStackTraceAsString(throwable), this);
            statusEnum = BizRetryStatusEnum.RETRY_FAIL;
        }

        this.setLastOpTime(new Date());
        this.retryTimes++;
        if (this.retryTimes >= this.retryBizConfig.getMaxRetryTimes()) {
            statusEnum = BizRetryStatusEnum.RETRY_STOP;
        }
        this.recordStatus = statusEnum;
    }

    public void setErrorMessage(String errorMessage) {
        if (StringUtils.length(errorMessage) > EdiConstants.RETRY_RECORD_MAX_ERROR_MESSAGE_LENGTH) {
            this.errorMessage = StringUtils.substring(errorMessage, 0, EdiConstants.RETRY_RECORD_MAX_ERROR_MESSAGE_LENGTH);
        }
    }
}
