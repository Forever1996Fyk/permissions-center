package com.ah.cloud.permissions.domain.message;

import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 21:12
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateChatRoomMessage {

    /**
     * 聊天室id
     */
    private Long roomId;

    /**
     * 状态
     */
    private Integer chatRoomStatus;
}
