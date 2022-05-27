package com.ah.cloud.permissions.netty.application.strategy.selector;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.netty.application.strategy.chatroom.operate.ChatRoomOperateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 14:44
 **/
@Component
public class ChatRoomOperateHandleSelector {
    @Resource
    private List<ChatRoomOperateHandler> list;

    public ChatRoomOperateHandler select(ChatRoomStatusEnum chatRoomStatusEnum) {
        for (ChatRoomOperateHandler chatRoomOperateHandler : list) {
            if (Objects.equals(chatRoomOperateHandler.getChatRoomStatusEnum(), chatRoomStatusEnum)) {
                return chatRoomOperateHandler;
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "ChatRoomOperateHandler");
    }
}
