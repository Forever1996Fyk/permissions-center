package com.ah.cloud.permissions.netty.application.manager;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-24 10:48
 **/
@Slf4j
@Component
public class ChatRoomStoreManager {
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    /**
     * 用户加入聊天室
     *
     * @param roomId
     * @param userId
     * @param maxSize
     */
    public void addUserToRoom(Long roomId, Long userId, Long maxSize) {
        String chatRoomKey = RedisKeyHelper.getChatRoomKey(roomId);
        Long size = redisCacheHandleStrategy.getSetSize(chatRoomKey);
        if (size >= maxSize) {
            throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_MAX_SIZE, String.valueOf(roomId));
        }
        redisCacheHandleStrategy.setAdd(chatRoomKey, userId);
    }

    /**
     * 用户离开聊天室
     *
     * @param roomId
     * @param userId
     */
    public void removeUserFromRoom(Long roomId, Long userId) {
        String chatRoomKey = RedisKeyHelper.getChatRoomKey(roomId);
        redisCacheHandleStrategy.setRemove(chatRoomKey, userId);
    }

    /**
     * 获取当前聊天室的人数
     * @param roomId
     * @return
     */
    public Long getRoomCurrentUserCount(Long roomId) {
        return redisCacheHandleStrategy.getSetSize(RedisKeyHelper.getChatRoomKey(roomId));
    }

    /**
     * 判断当前用户是否存在聊天室中
     * @param roomId
     * @param userId
     * @return
     */
    public boolean currentUserIsExistRoom(Long roomId, Long userId) {
        String chatRoomKey = RedisKeyHelper.getChatRoomKey(roomId);
        return redisCacheHandleStrategy.setExisted(chatRoomKey, userId);
    }
}
