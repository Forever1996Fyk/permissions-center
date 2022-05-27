package com.ah.cloud.permissions.netty.infrastructure.service.nofity;

import com.ah.cloud.permissions.enums.netty.NotifyTypeEnum;
import com.ah.cloud.permissions.netty.domain.notify.BaseNotify;

/**
 * @program: permissions-center
 * @description: 通知service
 * @author: YuKai Fan
 * @create: 2022-05-20 10:27
 **/
public interface NotifyService {

    /**
     * 消息通知
     * @param baseNotify
     */
    void notifyMessage(BaseNotify baseNotify);

    /**
     * 是否支持
     * @param baseNotify
     * @return
     */
    boolean support(BaseNotify baseNotify);


    /**
     * 通知类型
     * @return
     */
    NotifyTypeEnum getNotifyTypeEnum();
}
