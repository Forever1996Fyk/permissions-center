package com.ah.cloud.permissions.netty.infrastructure.coder;

import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.domain.dto.ReceiveMessageDTO;
import com.ah.cloud.permissions.netty.infrastructure.constant.MessageConstants;
import com.ah.cloud.permissions.proto.service.ReceiveMessageBody;
import com.google.common.base.Throwables;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 消息解码器
 * @author: YuKai Fan
 * @create: 2022-05-12 10:13
 **/
@Slf4j
public class FinalMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        // 先标记当前readIndex位置
        in.markReaderIndex();
        /*
        判断字节长度
        因为, 在真正的数据前，会加上自定义协议字节, 所以基础字节长度肯定要比自定义协议字节长
        如果字节长度不足，直接返回
         */
        if (in.readableBytes() < MessageConstants.MESSAGE_LENGTH) {
            log.warn("FinalMessageDecoder message length have not 8");
            return;
        }
        // 判断魔数
        short magic = in.readShort();
        if (magic != MessageConstants.MAGIC_CODE) {
            log.error("FinalMessageDecoder message decoder failed, reason is magic_code error, messageMagic is {}, address is [{}]", magic, ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        // 判断版本
        short version = in.readShort();
        if (version != MessageConstants.VERSION) {
            log.error("FinalMessageDecoder message decoder failed, reason is version error, version is {}, address is [{}]", version, ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        // 获取数据长度
        int length = in.readInt();
        if (length < 0) {
            log.error("FinalMessageDecoder message decoder failed, reason is data length error, length is 0, address is [{}]", ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        // 如果读到的消息长度小于传递的消息长度, 则重写读取位置, 并且直接返回
        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }
        byte[] array;
        if (in.hasArray()) {
            // 堆缓冲
            ByteBuf byteBuf = in.slice();
            array = byteBuf.array();
        } else {
            // 直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }
        // 转换字节为消息对象
        Object o = convertByteToMessage(array);
        if (Objects.isNull(o)) {
            return;
        }
        list.add(o);
    }

    /**
     * 字节数据转换
     * @param array
     * @return
     */
    private Object convertByteToMessage(byte[] array) {
        try {
            ReceiveMessageBody.ReceiveContent receiveContent = ReceiveMessageBody.ReceiveContent.parseFrom(array);
            return ReceiveMessageDTO.builder()
                    .content(receiveContent.getBody())
                    .formatEnum(FormatEnum.getByType(receiveContent.getFormat()))
                    .msgTypeEnum(MsgTypeEnum.getByType(receiveContent.getMsgType()))
                    .msgId(receiveContent.getMsgId())
                    .timestamp(receiveContent.getTimestamp())
                    .msgSourceEnum(MsgSourceEnum.getByType(receiveContent.getMsgSource()))
                    .build();
        } catch (InvalidProtocolBufferException e) {
            log.error("FinalMessageDecoder[convertByteToMessage] convert byte to message failed, reason is {}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
