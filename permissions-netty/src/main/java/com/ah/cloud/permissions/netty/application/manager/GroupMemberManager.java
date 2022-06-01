package com.ah.cloud.permissions.netty.application.manager;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 群组成员管理
 * @author: YuKai Fan
 * @create: 2022-05-27 17:20
 **/
@Component
public class GroupMemberManager {
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    /**
     * 添加群组成员
     *
     * @param groupId
     * @param memberId
     */
    public void addMemberByGroup(Long groupId, Long memberId) {
        String key = RedisKeyHelper.getGroupMemberKey(groupId);
        redisCacheHandleStrategy.setAdd(key, memberId);
    }

    /**
     * 根据群组id获取群成员集合
     * @param groupId
     * @return
     */
    public Set<Long> getMembersByGroupId(Long groupId) {
        String key = RedisKeyHelper.getGroupMemberKey(groupId);
        return redisCacheHandleStrategy.getSet(key);
    }

    /**
     * 移除群组用户
     *
     * @param groupId
     * @param memberId
     */
    public void removeMember(Long groupId, Long memberId) {
        String key = RedisKeyHelper.getGroupMemberKey(groupId);
        redisCacheHandleStrategy.setRemove(key, memberId);
    }
}
