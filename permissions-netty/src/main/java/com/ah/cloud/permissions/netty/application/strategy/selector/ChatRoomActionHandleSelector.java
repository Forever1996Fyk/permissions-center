package com.ah.cloud.permissions.netty.application.strategy.selector;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.application.strategy.chatroom.action.ChatRoomActionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:51
 **/
@Component
public class ChatRoomActionHandleSelector {
    @Resource
    private List<ChatRoomActionHandler> chatRoomActionHandlerList;

    public ChatRoomActionHandler select(ChatRoomActionEnum chatRoomActionEnum) {
        for (ChatRoomActionHandler chatRoomActionHandler : chatRoomActionHandlerList) {
            if (Objects.equals(chatRoomActionEnum, chatRoomActionHandler.getChatRoomActionEnum())) {
                return chatRoomActionHandler;
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "ChatRoomActionHandler");
    }
}
