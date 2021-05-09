package com.company.im.chat.server.chat;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.server.ServerNode;
import com.company.im.chat.serverhandle.IOHandle;
import com.company.im.chat.serverhandle.PacketDecoderHandle;
import com.company.im.chat.serverhandle.PacketEncoderHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**chat server:初始化，启动，关闭
 */
public class ChatServer implements ServerNode {

    private static Logger logger= LoggerFactory.getLogger(ChatServer.class);

    private EventLoopGroup bossGroup=new NioEventLoopGroup(1);

    private EventLoopGroup workerGroup=new NioEventLoopGroup();

    private int port;

    @Override
    public void initServer() {
        this.port= SpringContext.getServerConfig().getSocketPort();
    }

    @Override
    public void startServer() {
        try{
            //创建服务器的启动对象，设置参数
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            //设置两个线程组boosGroup、workerGroup
            serverBootstrap.group(bossGroup,workerGroup)
                    //设置服务器通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 给pipeline管道设置处理器
                            var pipeline=socketChannel.pipeline();
                            pipeline.addLast(new PacketDecoderHandle(
                                    1024*4,0,4,
                                    0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new PacketEncoderHandle());
                            //300s无响应便会触发UserEventTriggered事件到MessageTransportHandler
                            pipeline.addLast("idleStateHandle",new IdleStateHandler(
                                    0,0,300));
                            pipeline.addLast(new IOHandle());
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            //绑定端口号，启动服务端
            serverBootstrap.bind(port).sync();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return;
        }
        logger.info("chat server start success at [{}] ",port);
    }

    @Override
    public void closeServer() {
        if(null!=bossGroup){
            bossGroup.shutdownGracefully();
        }
        if(null!=workerGroup){
            workerGroup.shutdownGracefully();
        }
    }
}
