package com.company.im.chat.serverhandle;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.dispatcher.CmdTask;
import com.company.im.chat.common.SessionCloseReason;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.session.IOSession;
import com.company.im.chat.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/*
**IO读写，心跳及异常处理
 */
public class IOHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger= LoggerFactory.getLogger(IOHandle.class);

    /*
    **客户端请求连接，激活channel(添加新会话)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        var channel=ctx.channel();
        if(!ChannelUtil.addChannelSession(channel,new IOSession(channel))){
            logger.error("重复会话,远程IP："+ChannelUtil.getRemoteIp(channel));
        }
        logger.info("succeed register channel");
    }

    /*
    **客户端发送数据，接收数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        var channel=ctx.channel();
        AbstractPacket message=(AbstractPacket)msg;
        if(message==null){
            logger.error("空数据，请检查客户端发送情况,IP为："+ChannelUtil.getRemoteIp(channel));
            return;
        }
        logger.info("succeed receive pact, content is {}",
                message.getClass().getSimpleName());
        var session=ChannelUtil.getSession(channel);
        logger.info("dispatchKey is :{}",session.getDispatchKey());
        var userTask=new CmdTask(session.getDispatchKey(),message,session);
        //线程池处理用户消息
        SpringContext.getMessageDispatcher().submitTask(userTask);
    }

    /*
    **用户触发，心跳检测
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            var idleStateEvent=(IdleStateEvent)evt;
            if(idleStateEvent.state()== IdleState.ALL_IDLE){
                logger.error("用户读超时,即将登出");
                //用户长时间无响应登出
                var channel=ctx.channel();
                SpringContext.getUserService().logout(channel, SessionCloseReason.Exception);
                ctx.close();
            }
        }
    }

    /*
    **异常处理,用户登出处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("异常："+cause.getMessage()+",即将登出");
        var channel=ctx.channel();
        if((cause instanceof IOException)&&!ChannelUtil.isClose(channel)){
            //用户服务-用户登出
            SpringContext.getUserService().logout(channel,SessionCloseReason.Exception);
            logger.error("用户登出");
            ctx.close();
        }

    }
}
