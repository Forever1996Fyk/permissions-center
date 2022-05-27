package com.ah.cloud.permissions.netty.infrastructure.event.message;

import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.ah.cloud.permissions.netty.domain.dto.ReceiveMessageDTO;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description: 消息处理器
 * @author: YuKai Fan
 * @create: 2022-05-12 11:45
 **/
public interface MessageHandler extends InitializingBean {

    /**
     * 消息处理
     * @param context
     * @param dto
     */
    void handle(ChannelHandlerContext context, ReceiveMessageDTO dto);

    /**
     * 获取消息类型
     * @return
     */
    MsgTypeEnum getMsgTypeEnum();
}
