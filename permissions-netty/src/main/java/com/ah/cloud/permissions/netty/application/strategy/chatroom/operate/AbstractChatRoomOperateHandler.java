package com.ah.cloud.permissions.netty.application.strategy.chatroom.operate;

import com.ah.cloud.permissions.biz.application.service.ChatRoomService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ChatRoom;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.ChatRoomErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 14:30
 **/
@Slf4j
@Component
public abstract class AbstractChatRoomOperateHandler implements ChatRoomOperateHandler {
    @Resource
    private ChatRoomService chatRoomService;

    @Override
    public void handle(Long roomId) {
        ChatRoom chatRoom = chatRoomService.getOne(
                new QueryWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getRoomId, roomId)
                        .eq(ChatRoom::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(chatRoom)) {
            log.error("{}[handle] current chatroom not existed, roomId is {}", getLogMark(), roomId);
            throw new BizException(ChatRoomErrorCodeEnum.OPERATE_FAILED_CHATROOM_NOT_EXISTED, String.valueOf(roomId));
        }
        log.info("{}[handle] current chatroom start handle operate, params is {}", getLogMark(), JsonUtils.toJsonString(chatRoom));
        doHandle(chatRoom);
    }

    /**
     * 逻辑处理
     * @param chatRoom
     */
    protected abstract void doHandle(ChatRoom chatRoom);

    /**
     * 操作日志
     * @return
     */
    protected abstract String getLogMark();
}
