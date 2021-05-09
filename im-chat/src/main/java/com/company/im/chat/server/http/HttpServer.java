package com.company.im.chat.server.http;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.server.ServerNode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HttpServer implements ServerNode {

    private Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private int port;

    @Override
    public void initServer() {
        var  serverConfigs = SpringContext.getServerConfig();
        this.port = serverConfigs.getHttpPort();
    }

    @Override
    public void startServer() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.TRACE)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("http-decorder", new HttpRequestDecoder());
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(512 * 1024));
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    ch.pipeline().addLast("serve-handler", new HttpServerHandler());
                }
            });
            logger.info("http server start at [{}]", port);
            b.bind(new InetSocketAddress(port)).sync();
        }
        catch(Exception e){
            logger.info("http server start  wrong "+e.getMessage());
            return;
        }
        logger.info("http server start success at [{}]", port);
    }

    @Override
    public void closeServer() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
