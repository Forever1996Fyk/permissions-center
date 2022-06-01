package com.ah.cloud.permissions.biz.application.strategy.push.umeng;

import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.infrastructure.config.UmengMsgPushConfiguration;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.push.UmengErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 20:56
 **/
@Slf4j
@Component
public abstract class AbstractUmengMsgCastBuilder implements UmengMsgCastBuilder {
    @Resource
    protected UmengMsgPushConfiguration umengMsgPushConfiguration;
    /**
     * ios语音后缀
     */
    protected static final String IOS_VOICE_SUFFIX = ".caf";


    @Override
    public UmengNotification build(UmengMsgPush msgPush) {
        try {
            return doBuilder(msgPush);
        } catch (Exception e) {
            log.error("{} build msg cast error with exception, params is {}, reason is {}", getLogMark(), JsonUtils.toJSONString(msgPush), Throwables.getStackTraceAsString(e));
            throw new BizException(UmengErrorCodeEnum.MSG_APP_PUSH_BUILDER_CAST_FAILED);
        }
    }

    /**
     * 构建
     * @param msgPush
     * @return
     * @throws Exception
     */
    protected abstract UmengNotification doBuilder(UmengMsgPush msgPush) throws Exception;

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();
}
