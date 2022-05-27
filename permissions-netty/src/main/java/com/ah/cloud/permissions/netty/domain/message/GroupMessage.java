package com.ah.cloud.permissions.netty.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-14 14:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 发送者id
     */
    private Long fromUserId;

    /**
     * 发送内容
     */
    private String content;
}
