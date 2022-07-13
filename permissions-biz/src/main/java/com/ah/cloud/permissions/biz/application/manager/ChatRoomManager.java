package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.ChatRoomHelper;
import com.ah.cloud.permissions.biz.application.service.ChatRoomService;
import com.ah.cloud.permissions.biz.domain.chatroom.form.ChatRoomAddForm;
import com.ah.cloud.permissions.biz.domain.chatroom.form.ChatRoomUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.producer.OperateChatRoomProducer;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ChatRoom;
import com.ah.cloud.permissions.domain.message.OperateChatRoomMessage;
import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import com.ah.cloud.permissions.enums.netty.ChatRoomErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 聊天室管理
 * @author: YuKai Fan
 * @create: 2022-05-23 16:39
 **/
@Slf4j
@Component
public class ChatRoomManager {
    @Resource
    private ChatRoomHelper chatRoomHelper;
    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private OperateChatRoomProducer operateChatRoomProducer;

    /**
     * 创建聊天室
     *
     * @param form
     */
    public void createChatRoom(ChatRoomAddForm form) {
        ChatRoom chatRoom = chatRoomHelper.convertToEntity(form);
        chatRoomService.save(chatRoom);
    }

    /**
     * 更新聊天室
     *
     * @param form
     */
    public void updateChatRoom(ChatRoomUpdateForm form) {
        ChatRoom existChatRoom = chatRoomService.getOne(
                new QueryWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getId, form.getId())
                        .eq(ChatRoom::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existChatRoom)) {
            throw new BizException(ChatRoomErrorCodeEnum.OPERATE_FAILED_CHATROOM_NOT_EXISTED, String.valueOf(form.getId()));
        }

        ChatRoom chatRoom = chatRoomHelper.convertToEntity(form);
        chatRoom.setVersion(existChatRoom.getVersion());
        boolean updateResult = chatRoomService.update(
                chatRoom,
                new UpdateWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getId, form.getId())
                        .eq(ChatRoom::getVersion, existChatRoom.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 根据id删除聊天室
     *
     * @param id
     */
    public void deleteChatRoomById(Long id) {
        ChatRoom existChatRoom = chatRoomService.getOne(
                new QueryWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getId, id)
                        .eq(ChatRoom::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existChatRoom)) {
            throw new BizException(ChatRoomErrorCodeEnum.OPERATE_FAILED_CHATROOM_NOT_EXISTED, String.valueOf(id));
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setDeleted(existChatRoom.getDeleted());
        boolean deleteResult = chatRoomService.update(
                chatRoom,
                new UpdateWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getId, id)
        );
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 打开聊天室
     *
     * @param roomId
     */
    public void openChatRoomByRoomId(Long roomId) {
        operateChatRoom(roomId, ChatRoomStatusEnum.OPEN);
    }


    /**
     * 关闭聊天室
     *
     * @param roomId
     */
    public void closeChatRoomByRoomId(Long roomId) {
        operateChatRoom(roomId, ChatRoomStatusEnum.CLOSE);
    }

    private void operateChatRoom(Long roomId, ChatRoomStatusEnum chatRoomStatusEnum) {
        ChatRoom chatRoom = chatRoomService.getOne(
                new QueryWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getRoomId, roomId)
                        .eq(ChatRoom::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(chatRoom)) {
            throw new BizException(ChatRoomErrorCodeEnum.OPERATE_FAILED_CHATROOM_NOT_EXISTED, String.valueOf(roomId));
        }
        ChatRoom updateChatRoom = new ChatRoom();
        updateChatRoom.setStatus(chatRoomStatusEnum.getStatus());
        updateChatRoom.setVersion(chatRoom.getVersion());
        boolean updateResult = chatRoomService.update(
                updateChatRoom,
                new UpdateWrapper<ChatRoom>().lambda()
                        .eq(ChatRoom::getRoomId, roomId)
                        .eq(ChatRoom::getVersion, chatRoom.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        // 通知操作聊天室
        OperateChatRoomMessage operateChatRoomMessage = OperateChatRoomMessage.builder().roomId(roomId).chatRoomStatus(chatRoomStatusEnum.getStatus()).build();
        operateChatRoomProducer.sendMessage(operateChatRoomMessage);
    }
}
