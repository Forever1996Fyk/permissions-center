package com.ah.cloud.permissions.netty.infrastructure.coder;

import com.ah.cloud.permissions.netty.domain.message.Transportable;
import com.ah.cloud.permissions.netty.infrastructure.constant.MessageConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: permissions-center
 * @description: 消息编码器
 * @author: YuKai Fan
 * @create: 2022-05-12 11:15
 **/
public class FinalMessageEncoder extends MessageToByteEncoder<Transportable> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Transportable data, ByteBuf out) throws Exception {
        byte[] body = data.getBody();
        out.writeShort(MessageConstants.MAGIC_CODE);
        out.writeShort(MessageConstants.VERSION);
        out.writeInt(data.getLength());
        // 可以对body进行加密发送
        out.writeBytes(body);
    }
}
