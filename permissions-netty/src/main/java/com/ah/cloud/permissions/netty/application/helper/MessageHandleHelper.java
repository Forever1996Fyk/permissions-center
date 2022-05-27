package com.ah.cloud.permissions.netty.application.helper;

import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-19 15:20
 **/
@Component
public class MessageHandleHelper {

    public MessageBody<String> convertToBody(MessageNodeDTO messageNodeDTO) {
        return MessageBody.<String>builder()
                .data(messageNodeDTO.getContent())
                .formatEnum(FormatEnum.getByType(messageNodeDTO.getFormatType()))
                .msgSourceEnum(MsgSourceEnum.getByType(messageNodeDTO.getMsgSource()))
                .msgTypeEnum(MsgTypeEnum.getByType(messageNodeDTO.getMsgType()))
                .msgId(messageNodeDTO.getMsgId())
                .fromId(messageNodeDTO.getFromId())
                .toId(messageNodeDTO.getToId())
                .build();
    }
}
