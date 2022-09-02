package com.ah.cloud.permissions.netty.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.MessageOffline;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.MessageRecord;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-18 10:43
 **/
@Component
public class MessageStoreHelper {

    public <T> MessageRecord convert(MessageBody<T> body) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setContent(JsonUtils.toJsonString(body.getData()));
        messageRecord.setFromId(body.getFromId());
        messageRecord.setToId(body.getToId());
        messageRecord.setFormat(body.getFormatEnum().getType());
        messageRecord.setMsgId(body.getMsgId());
        messageRecord.setMsgSource(body.getMsgSourceEnum().getType());
        messageRecord.setMsgType(body.getMsgTypeEnum().getType());
        messageRecord.setMsgTime(new Date());
        return messageRecord;
    }

    public <T> MessageOffline convertToOffline(MessageBody<T> body) {
        MessageOffline messageOffline = new MessageOffline();
        messageOffline.setMsgId(body.getMsgId());
        messageOffline.setContent(JsonUtils.toJsonString(body.getData()));
        messageOffline.setMsgType(body.getMsgTypeEnum().getType());
        messageOffline.setToId(body.getToId());
        messageOffline.setMsgTime(new Date());
        messageOffline.setFormat(body.getFormatEnum().getType());
        messageOffline.setMsgSource(body.getMsgSourceEnum().getType());
        messageOffline.setHasRead(YesOrNoEnum.NO.getType());
        messageOffline.setFromId(body.getFromId());
        return messageOffline;
    }

    public MessageBody<String> convertToBody(MessageOffline messageOffline) {
        return MessageBody.<String>builder()
                .msgId(messageOffline.getMsgId())
                .msgTypeEnum(MsgTypeEnum.getByType(messageOffline.getMsgType()))
                .formatEnum(FormatEnum.getByType(messageOffline.getFormat()))
                .msgSourceEnum(MsgSourceEnum.getByType(messageOffline.getMsgSource()))
                .data(messageOffline.getContent())
                .sequenceId(AppUtils.generateSequenceId())
                .toId(messageOffline.getToId())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public List<MessageBody<String>> convertToBody(List<MessageOffline> messageOfflineList) {
        return messageOfflineList.stream()
                .map(this::convertToBody)
                .collect(Collectors.toList());
    }
}
