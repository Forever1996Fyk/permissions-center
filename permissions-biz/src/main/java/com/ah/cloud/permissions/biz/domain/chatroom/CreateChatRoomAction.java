package com.ah.cloud.permissions.biz.domain.chatroom;

import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 创建聊天室
 * @author: YuKai Fan
 * @create: 2022-05-23 16:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomAction implements BaseAction {

    /**
     * 聊天室id
     */
    private Long roomId;

    /**
     * 聊天室名称
     */
    private String roomName;

    /**
     * 最大聊天室数量
     */
    private Long maxRoomSize;

    /**
     * 聊天室头像
     */
    private String roomAvatar;

    /**
     * 所属id
     */
    private Long ownerId;

    @Override
    public ChatRoomActionEnum getChatRoomActionEnum() {
        return ChatRoomActionEnum.CREATE;
    }
}
