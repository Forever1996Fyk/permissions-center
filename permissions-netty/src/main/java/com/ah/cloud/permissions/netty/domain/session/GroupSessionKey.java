package com.ah.cloud.permissions.netty.domain.session;

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
     * 来源类型
     */
    private GroupTypeEnum groupTypeEnum;
}
