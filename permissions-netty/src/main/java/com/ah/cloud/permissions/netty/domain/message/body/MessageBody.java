package com.ah.cloud.permissions.netty.domain.message.body;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.NullUtils;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.message.Transportable;
import com.ah.cloud.permissions.proto.service.SendMessageBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 14:44
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody<T> implements Transportable {
    /**
     * 消息id uuid
     */
    private Long msgId;

    /**
     * 序列化ID，可以用作异步处理
     */
    private int sequenceId;

    /**
     * 消息格式
     */
    private FormatEnum formatEnum;

    /**
     * 消息类型
     */
    private MsgTypeEnum msgTypeEnum;

    /**
     * 消息来源
     */
    private MsgSourceEnum msgSourceEnum;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 发送id
     */
    private Long  fromId;

    /**
     * 接收id
     */
    private Long toId;

    /**
     * 消息数据
     */
    private T data;

    @Override
    public byte[] getBody() {
        SendMessageBody.SendContent sendContent = SendMessageBody.SendContent.newBuilder()
                .setBody(JsonUtils.toJsonString(getData()))
                .setFormat(NullUtils.of(getFormatEnum().getType()))
                .setMsgId(NullUtils.of(getMsgId()))
                .setMsgType(NullUtils.of(getMsgTypeEnum().getType()))
                .setSequenceId(NullUtils.of(getSequenceId()))
                .setTimestamp(NullUtils.of(getTimestamp()))
                .build();
        return sendContent.toByteArray();
    }

    @Override
    public int getLength() {
        return getBody().length;
    }
}
