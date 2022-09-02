package com.ah.cloud.permissions.netty.domain.message.body;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.NullUtils;
import com.ah.cloud.permissions.netty.domain.message.Transportable;
import com.ah.cloud.permissions.proto.service.SendMessageBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 15:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexMessageBody<T> implements Transportable {

    /**
     * 消息体集合
     */
    private List<MessageBody<T>> messageBodyList;

    @Override
    public byte[] getBody() {
        return SendMessageBody.ComplexSendContent.newBuilder()
                .addAllSendContentList(
                        this.getMessageBodyList().stream()
                                .map(this::buildSendContent)
                                .collect(Collectors.toList())
                )
                .build()
                .toByteArray();
    }

    private SendMessageBody.SendContent buildSendContent(MessageBody<T> body) {
        return SendMessageBody.SendContent.newBuilder()
                .setBody(JsonUtils.toJsonString(body.getData()))
                .setFormat(NullUtils.of(body.getFormatEnum().getType()))
                .setMsgId(NullUtils.of(body.getMsgId()))
                .setMsgType(NullUtils.of(body.getMsgTypeEnum().getType()))
                .setSequenceId(NullUtils.of(body.getSequenceId()))
                .setTimestamp(NullUtils.of(body.getTimestamp()))
                .build();
    }

    @Override
    public int getLength() {
        return this.getBody().length;
    }
}
