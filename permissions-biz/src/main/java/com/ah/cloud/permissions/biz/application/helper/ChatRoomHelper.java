package com.ah.cloud.permissions.biz.application.helper;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.domain.chatroom.form.ChatRoomAddForm;
import com.ah.cloud.permissions.biz.domain.chatroom.form.ChatRoomUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ChatRoom;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 17:27
 **/
@Component
public class ChatRoomHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public ChatRoom convertToEntity(ChatRoomAddForm form) {
        ChatRoom chatRoom = Convert.INSTANCE.convert(form);
        chatRoom.setRoomId(RandomUtil.randomLong(6));
        chatRoom.setCreator(SecurityUtils.getUserNameBySession());
        chatRoom.setModifier(SecurityUtils.getUserNameBySession());
        return chatRoom;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public ChatRoom convertToEntity(ChatRoomUpdateForm form) {
        ChatRoom chatRoom = Convert.INSTANCE.convert(form);
        chatRoom.setCreator(SecurityUtils.getUserNameBySession());
        chatRoom.setModifier(SecurityUtils.getUserNameBySession());
        return chatRoom;
    }

    @Mapper
    public interface Convert {
        ChatRoomHelper.Convert INSTANCE = Mappers.getMapper(ChatRoomHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        ChatRoom convert(ChatRoomAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        ChatRoom convert(ChatRoomUpdateForm form);
    }
}
