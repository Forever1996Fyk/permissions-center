package com.ah.cloud.permissions.netty.infrastructure.service.nofity;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.NotifyTypeEnum;
import com.ah.cloud.permissions.netty.domain.dto.notify.OfflineNotifyDTO;
import com.ah.cloud.permissions.netty.domain.notify.BaseNotify;
import com.ah.cloud.permissions.netty.domain.notify.OfflineNotify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 10:46
 **/
@Slf4j
@Component
public class OfflineNotifyService extends AbstractNotifyService<OfflineNotifyDTO, OfflineNotify> {
    private final static String LOG_MARK = "OfflineNotifyService";

    @Override
    protected void doHandle(BaseNotify baseNotify) {
        OfflineNotify offlineNotify = (OfflineNotify) baseNotify;
        notifyClientService.simpleSend(
                ImmutablePair.of(offlineNotify.getSingleSessionKey(), offlineNotify.getSession()),
                buildMessageBody(offlineNotify)
        );
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public boolean support(BaseNotify baseNotify) {
        return baseNotify.getClass().isAssignableFrom(OfflineNotify.class);
    }

    @Override
    public NotifyTypeEnum getNotifyTypeEnum() {
        return NotifyTypeEnum.OFFLINE;
    }

    @Override
    protected OfflineNotifyDTO buildContent(OfflineNotify offlineNotify) {
        return OfflineNotifyDTO.builder()
                .offlineReasonType(offlineNotify.getOfflineReasonEnum().getType())
                .offlineTime(DateUtils.getStrCurrentTime())
                .offlineNotice(offlineNotify.getContent())
                .build();
    }

    @Override
    protected FormatEnum getFormatEnum() {
        return FormatEnum.TEXT;
    }
}
