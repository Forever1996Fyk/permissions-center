package com.ah.cloud.permissions.netty.infrastructure.init;

import com.ah.cloud.permissions.netty.infrastructure.coder.FinalMessageDecoder;
import com.ah.cloud.permissions.netty.infrastructure.coder.FinalMessageEncoder;
import com.ah.cloud.permissions.netty.infrastructure.handler.AcceptorIdleStateTrigger;
import com.ah.cloud.permissions.netty.infrastructure.handler.FinalChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 22:11
 **/
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
    /**
     *  读空闲时间(秒)
     */
    public static final int READ_IDLE_TIME = 150;

    /**
     *  写接空闲时间(秒)
     */
    public static final int WRITE_IDLE_TIME = 120;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //webSocket基于Http协议, 所以要有Http相应的编解码器
        pipeline.addLast(new HttpServerCodec());
        //对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合, 集合成FullHttpRequest或FullHttpResponse
        //几乎在Netty中的编程, 都会用到此handle
        pipeline.addLast(new HttpObjectAggregator(65536));
        //自定义编码解码器
        pipeline.addLast(new FinalMessageEncoder());
        pipeline.addLast(new FinalMessageDecoder());

        // 心跳机制处理
        pipeline.addLast(new IdleStateHandler(READ_IDLE_TIME, WRITE_IDLE_TIME, 0, TimeUnit.SECONDS));

        // 自定义心跳处理
        pipeline.addLast(AcceptorIdleStateTrigger.getInstance());

        /*
          webSocket服务处理的协议, 用于指定给客户连接的路由: /ws
          本handle会处理一些繁重的操作:
          握手动作: handshaking(close, ping, pong) ping + pong = 心跳
          对于webSocket来说, 数据已frames进行传输的, 不同的数据类型对应的frames不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        //鉴权handler
//        pipeline.addLast(AuthenticationHandler.getInstance(permits));
        pipeline.addLast(FinalChannelHandler.getInstance());
    }
}
