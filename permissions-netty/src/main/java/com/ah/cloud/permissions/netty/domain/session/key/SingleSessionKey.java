package com.ah.cloud.permissions.netty.domain.session.key;

import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-17 15:19
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SingleSessionKey implements SessionKey {

    /**
     * 会话id
     */
    private Long sessionId;

    /**
     * 来源类型
     */
    private MsgSourceEnum msgSourceEnum;
}
