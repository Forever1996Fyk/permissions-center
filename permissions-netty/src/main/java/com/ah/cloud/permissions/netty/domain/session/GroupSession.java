package com.ah.cloud.permissions.netty.domain.session;

import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.Server;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 16:41
 **/
@Getter
@Builder
public class GroupSession implements ServerSession {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 当前用户channel
     */
    private transient Channel channel;

    /**
     * 当前节点 群组成员id集合
     */
    private Set<Long> groupMemberIdList;

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Integer getPort() {
        return null;
    }
}
