package com.ah.cloud.permissions.netty.infrastructure.service.nofity;

import com.ah.cloud.permissions.biz.application.provider.ValidateCodeProvider;
import com.ah.cloud.permissions.biz.domain.code.ValidateResult;
import com.ah.cloud.permissions.netty.domain.notify.BaseNotify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 14:20
 **/
@Slf4j
@Component
public class NotifySelector {
    @Resource
    private List<NotifyService> notifyServiceList;

    /**
     * 消息通知
     * @param baseNotify
     */
    public void notifyMessage(BaseNotify baseNotify) {
        int currentPosition = 0;
        int size = this.notifyServiceList.size();
        for (NotifyService notifyService : notifyServiceList) {
            if (!notifyService.support(baseNotify)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("NotifyService handle with %s (%d/%d)",
                        notifyService.getClass().getSimpleName(), ++currentPosition, size)));
            }
            notifyService.notifyMessage(baseNotify);
        }
    }

}
