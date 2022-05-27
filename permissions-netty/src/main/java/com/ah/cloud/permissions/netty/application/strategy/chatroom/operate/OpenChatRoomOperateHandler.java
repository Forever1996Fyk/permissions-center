package com.ah.cloud.permissions.netty.application.strategy.chatroom.operate;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ChatRoom;
import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 14:33
 **/
@Slf4j
@Component
public class OpenChatRoomOperateHandler extends AbstractChatRoomOperateHandler {
    private final static String LOG_MARK = "OpenChatRoomOperateHandler";

    @Override
    protected void doHandle(ChatRoom chatRoom) {
        GroupSessionService<ChatRoomSession, GroupSessionKey> groupSessionService = SessionManager.getChatRoomSessionService();
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(chatRoom.getRoomName(), GlobalEventExecutor.INSTANCE);
        ChatRoomSession chatRoomSession = ChatRoomSession.builder()
                .roomId(chatRoom.getRoomId())
                .channelGroup(channelGroup)
                .groupStatus(ChatRoomSession.GroupStatus.ENABLED)
                .maxSize(chatRoom.getMaxRoomSize())
                .build();
        groupSessionService.save(chatRoomSession);
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public ChatRoomStatusEnum getChatRoomStatusEnum() {
        return ChatRoomStatusEnum.OPEN;
    }
}
