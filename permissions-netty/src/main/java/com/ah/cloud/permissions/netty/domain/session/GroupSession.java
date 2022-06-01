package com.ah.cloud.permissions.netty.domain.session;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class GroupSession implements ServerSession {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 当前用户channel
     */
    private transient Channel channel;

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Integer getPort() {
        return null;
    }
}
