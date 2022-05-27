package com.ah.cloud.permissions.domain.dto.netty.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 18:13
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfoDTO {

    /**
     * 聊天室id
     */
    private Long roomId;

    /**
     * 聊天室最大人数
     */
    private Long maxRoomSize;

    /**
     * 聊天室人员集合
     */
    private List<Long> roomUserIdList;
}
