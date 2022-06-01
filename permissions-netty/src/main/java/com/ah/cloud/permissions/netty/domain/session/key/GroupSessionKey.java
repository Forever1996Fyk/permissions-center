package com.ah.cloud.permissions.netty.domain.session.key;

import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-24 08:19
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GroupSessionKey implements SessionKey {

    /**
     * 会话id
     */
    private Long sessionId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 聊天室默认chat_room类型
     * @return
     */
    public GroupTypeEnum getGroupTypeEnum() {
        return GroupTypeEnum.GROUP;
    }
}
