package com.ah.cloud.permissions.netty.domain.session;

import com.ah.cloud.permissions.enums.YesOrNoEnum;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.*;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 16:25
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomSession implements ServerSession {

    /**
     * 通道
     */
    private transient ChannelGroup channelGroup;

    /**
     * 群组id
     */
    private Long roomId;

    /**
     * 群组状态
     */
    private GroupStatus groupStatus;

    /**
     * 是否禁言
     */
    private YesOrNoEnum isBannedChat;

    /**
     * 最大数量
     */
    private Long maxSize;

    public enum GroupStatus {
        /**
         * 可用
         */
        ENABLED(1, "可用"),

        /**
         * 不可用
         */
        DISABLED(2, "不可用"),
        ;

        private final int status;

        private final String desc;

        GroupStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }

        public boolean isEnabled() {
            return Objects.equals(this, ENABLED);
        }

        public boolean isDisabled() {
            return Objects.equals(this, DISABLED);
        }
    }

    /**
     * 关闭GroupSession
     */
    public void closeGroupSession() {
        this.groupStatus = GroupStatus.DISABLED;
    }


    /**
     * 打开GroupSession
     */
    public void openGroupSession() {
        this.groupStatus = GroupStatus.ENABLED;
    }


    @Override
    public Channel getChannel() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Integer getPort() {
        return null;
    }

}
