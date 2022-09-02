package com.ah.cloud.permissions.netty.infrastructure.service.nofity;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.message.NotifyMessage;
import com.ah.cloud.permissions.netty.domain.notify.BaseNotify;
import com.ah.cloud.permissions.netty.infrastructure.service.client.NotifySendClientService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 11:27
 **/
@Slf4j
public abstract class AbstractNotifyService<T, N extends BaseNotify> implements NotifyService {
    @Resource
    protected NotifySendClientService notifyClientService;

    @Override
    public void notifyMessage(BaseNotify baseNotify) {
        try {
            log.info("{}[notify] start handle notify, params is {}", getLogMark(), JsonUtils.toJsonString(baseNotify));
            doHandle(baseNotify);
        } catch (Exception e) {
            log.error("{}[notify] handle notify failed, params is {}, reason is {}", getLogMark(), JsonUtils.toJsonString(baseNotify), Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 构造内容
     * @param n
     * @return
     */
    protected abstract T buildContent(N n);

    /**
     * 消息处理
     * @param baseNotify
     */
    protected abstract void doHandle(BaseNotify baseNotify);

    /**
     * 获取消息格式
     * @return
     */
    protected abstract FormatEnum getFormatEnum();

    protected  MessageBody<NotifyMessage<T>> buildMessageBody(N baseNotify) {
        return MessageBody.<NotifyMessage<T>>builder()
                .msgSourceEnum(MsgSourceEnum.SERVER)
                .timestamp(System.currentTimeMillis())
                .msgTypeEnum(MsgTypeEnum.NOTIFY)
                .formatEnum(getFormatEnum())
                .toId(baseNotify.getSessionKey().getSessionId())
                .msgId(AppUtils.randomLongId())
                .sequenceId(AppUtils.generateSequenceId())
                .data(NotifyMessage.<T>builder().notifyType(getNotifyTypeEnum().getType()).content(buildContent(baseNotify)).build())
                .build();
    }
}
