package com.ah.cloud.permissions.netty.application.strategy.chatroom.operate;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ChatRoom;
import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 14:42
 **/
@Component
public class CloseChatRoomOperateHandler extends AbstractChatRoomOperateHandler {
    private final static String LOG_MARK = "CloseChatRoomOperateHandler";

    @Override
    protected void doHandle(ChatRoom chatRoom) {
        GroupSessionService<ChatRoomSession, GroupSessionKey> groupSessionService = SessionManager.getChatRoomSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder()
                .sessionId(chatRoom.getRoomId())
                .groupTypeEnum(GroupTypeEnum.CHAT_ROOM).build();
        ChatRoomSession chatRoomSession = groupSessionService.get(groupSessionKey);
        chatRoomSession.closeGroupSession();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public ChatRoomStatusEnum getChatRoomStatusEnum() {
        return ChatRoomStatusEnum.CLOSE;
    }
}
