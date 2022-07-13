package com.ah.cloud.permissions.netty.infrastructure.server;

import com.ah.cloud.permissions.enums.netty.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.init.WebSocketInitializer;
import com.google.common.base.Throwables;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 21:53
 **/
@Slf4j
public class WebSocketServerLaunch {

    /**
     * 连接socket线程池
     */
    private EventLoopGroup mainGroup;

    /**
     * 消息处理线程池
     */
    private EventLoopGroup workerGroup;

    /**
     * 服务端启动器
     */
    private ServerBootstrap serverBootstrap;

    /**
     * 端口号
     */
    private final int port;

    public WebSocketServerLaunch(int port) {
        this.port = port;
    }

    /**
     * 启动netty server
     */
    public void start() {
        mainGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = createServerBootstrap(mainGroup, workerGroup);
        serverBootstrap.childHandler(new WebSocketInitializer());
        ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
        channelFuture.channel().closeFuture().addListener(future -> this.destroy(mainGroup, workerGroup));
    }

    /**
     * 客户端连接
     * @param session
     * @param onSuccessFuture
     * @return
     */
    public Channel connect(ServerSession session, OnSuccessFuture onSuccessFuture) {
        if (Objects.isNull(session)) {
            throw new IMBizException(IMErrorCodeEnum.CLIENT_CONNECT_FAILED_SESSION_NULL);
        }
        try {
            ChannelFuture future = serverBootstrap.bind(session.getHost(), session.getPort()).sync().addListener(onSuccessFuture::onFuture);
            return future.channel();
        } catch (InterruptedException e) {
            log.error("WebSocketServerLaunch[connect] client connect failed, reason is {}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * 处理请求线程组销毁
     * @param mainGroup
     * @param workerGroup
     */
    public void destroy(EventLoopGroup mainGroup, EventLoopGroup workerGroup) {
        if (mainGroup != null && !mainGroup.isShuttingDown() && !mainGroup.isShutdown()) {
            try {
                mainGroup.shutdownGracefully();
            } catch (Exception e){
                log.error("WebSocketServerLaunch[destroy] netty server destroy error, reason is {}", Throwables.getStackTraceAsString(e));
            }
        }

        if (workerGroup != null && !workerGroup.isShuttingDown() && !workerGroup.isShutdown()) {
            try {
                workerGroup.shutdownGracefully();
            } catch (Exception e){
                log.error("WebSocketServerLaunch[destroy] netty server destroy error, reason is {}", Throwables.getStackTraceAsString(e));
            }
        }
    }


    @FunctionalInterface
    public interface OnSuccessFuture {
        /**
         * onFuture
         * @param future future
         */
        void onFuture(Future<?> future);
    }

    private ServerBootstrap createServerBootstrap(EventLoopGroup mainGroup, EventLoopGroup workerGroup) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(mainGroup, workerGroup);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.channel(NioServerSocketChannel.class);
        return bootstrap;
    }
}
