package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.application.manager.TokenManager;
import com.ah.cloud.permissions.biz.domain.user.DeviceInfo;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.enums.netty.OfflineReasonEnum;
import com.ah.cloud.permissions.netty.application.helper.SessionHelper;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.BindMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.notify.OfflineNotify;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.config.NettyProperties;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.service.client.SingleSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.nofity.NotifySelector;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 绑定消息处理器
 * @author: YuKai Fan
 * @create: 2022-05-12 14:30
 **/
@Slf4j
@Component
public class BindMessageHandler extends AbstractMessageHandler<BindMessage> {
    private final static String LOG_MARK = "BindMessageHandler";
    @Resource
    private TokenManager tokenManager;
    @Resource
    private SessionHelper sessionHelper;
    @Resource
    private NotifySelector notifySelector;
    @Resource
    private NettyProperties nettyProperties;
    @Resource
    private MessageStoreManager messageStoreManager;
    @Resource
    private SingleSendClientService singleClientService;

    @Override
    protected BindMessage convert(String message) {
        return JsonUtils.stringToBean(message, BindMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<BindMessage> body) {
        BindMessage message = body.getData();
        LocalUser localUser = tokenManager.getLocalUserByToken(message.getToken());
        if (Objects.isNull(localUser)) {
            // 用户未登录
            log.warn("BindMessageHandler[doHandle] user bind failed token is wrong, body is {}", JsonUtils.toJSONString(body));
            throw new IMBizException(IMErrorCodeEnum.BIND_SERVER_FAILED_TOKEN_ERROR);
        }
        Long userId = localUser.getUserInfo().getUserId();
        DeviceInfo deviceInfo = localUser.getDeviceInfo();
        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        /*
        如果已存在当前SingleSession, 则强制通知下线
        这里有两种情况:
        1. 同一个platform绑定, 但是设备不同 那么可以直接强制老设备下线;
        2. 同一个platform绑定, 但是设备相同
            这种情况可能是, 同一个设备重复连接, 那么则关闭旧连接。
            注: 这种情况一般是客户端断网，联网又重新链接上来，之前的旧链接没有来得及通过心跳机制关闭，在这里手动关闭
        那么这种情况只能通过deviceId区别
         */
        SingleSessionKey singleSessionKey = SingleSessionKey.builder().sessionId(userId).msgSourceEnum(body.getMsgSourceEnum()).build();
        if (singleSessionService.exist(singleSessionKey)) {
            SingleSession oldSession = singleSessionService.get(singleSessionKey);
            if (Objects.equals(oldSession.getDeviceId(), deviceInfo.getDeviceId())) {
                // 通知老设备下线
                OfflineNotify offlineNotify = OfflineNotify.builder()
                        .session(oldSession)
                        .sessionKey(singleSessionKey)
                        .offlineReasonEnum(OfflineReasonEnum.OTHER_DEVICE_LOGIN_FORCE_OFFLINE)
                        .content(OfflineReasonEnum.OTHER_DEVICE_LOGIN_FORCE_OFFLINE.getDesc(deviceInfo.getDeviceId()))
                        .build();
                notifySelector.notifyMessage(offlineNotify);
            }
            singleSessionService.remove(singleSessionKey);
        }
        SingleSession singleSession = sessionHelper.buildSingSession(context.channel(), body.getFromId(), body.getMsgSourceEnum(), nettyProperties.getPort(), deviceInfo);
        singleSessionService.save(singleSession);
        singleClientService.ack(context.channel(), body);

        // 绑定成功 发送未签收的消息
        List<MessageBody<String>> messageBodyList = messageStoreManager.listOfflineMessage(userId);
        singleClientService.complexSendMessageBodyList(ImmutablePair.of(singleSessionKey, singleSession), messageBodyList, list -> messageStoreManager.clearOfflineMessage(userId));
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected MessageHandler getCurrentMessageHandler() {
        return this;
    }

    @Override
    public MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.BIND;
    }
}
