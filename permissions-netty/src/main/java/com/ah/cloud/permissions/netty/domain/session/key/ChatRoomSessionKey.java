package com.ah.cloud.permissions.netty.domain.session.key;

import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-27 10:13
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomSessionKey implements SessionKey {

    /**
     * 会话id
     */
    private Long sessionId;

    /**
     * 聊天室默认chat_room类型
     * @return
     */
    public GroupTypeEnum getGroupTypeEnum() {
        return GroupTypeEnum.CHAT_ROOM;
    }
}
