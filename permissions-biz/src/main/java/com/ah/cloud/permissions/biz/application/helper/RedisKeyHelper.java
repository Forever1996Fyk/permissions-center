package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.constant.CacheConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 21:51
 **/
public class RedisKeyHelper {

    /**
     * 获取im 分布式session key
     *
     * @param userId
     * @return
     */
    public static String getImDistributionSessionKey(Long userId) {
        return CacheConstants.IM_DISTRIBUTION_SESSION_PREFIX + userId;
    }

    /**
     * 获取监听im 监听节点key
     * @param host
     * @param port
     * @return
     */
    public static String getImListenerNodeKey(String host, Integer port) {
        return CacheConstants.IM_LISTENER_NODE_PREFIX + host + PermissionsConstants.COLON_SEPARATOR + port;
    }

    /**
     * 获取离线消息列表key
     * @param toId
     * @return
     */
    public static String getOfflineMessageListKey(Long toId) {
        return CacheConstants.OFFLINE_MESSAGE_LIST_PREFIX + PermissionsConstants.COLON_SEPARATOR + toId;
    }

    /**
     * 获取聊天室key
     * @param roomId
     * @return
     */
    public static String getChatRoomKey(Long roomId) {
        return CacheConstants.CHAT_ROOM_PREFIX + roomId;
    }

    /**
     * 获取群组成员key
     * @param groupId
     * @return
     */
    public static String getGroupMemberKey(Long groupId) {
        return CacheConstants.GROUP_MEMBER_KE_PREFIX + groupId;
    }
}
