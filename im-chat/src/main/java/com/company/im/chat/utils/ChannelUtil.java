package com.company.im.chat.utils;

import com.company.im.chat.session.IOSession;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public final class ChannelUtil {

    private static final Logger logger= LoggerFactory.getLogger(ChannelUtil.class);

    public static AttributeKey<IOSession> session_Key=AttributeKey.valueOf("Session");

    /*
    *在channel上添加新会话
     */
    public static boolean addChannelSession(Channel channel,IOSession session){
        Attribute<IOSession> attribute=channel.attr(session_Key);
        return attribute.compareAndSet(null,session);
    }

    /*
    *从attribute中获取session(某一个channel)
     */
    public static IOSession getSession(Channel channel){
        Attribute<IOSession> attribute=channel.attr(session_Key);
        return attribute.get();
    }

    /*
    *从channel中获取远程IP地址
     */
    public static String getRemoteIp(Channel channel){
        InetSocketAddress inetSocketAddress=(InetSocketAddress) channel.remoteAddress();
        if(inetSocketAddress==null){
            logger.error("${channel}远程IP获取失败");
            return "";
        }
        return inetSocketAddress.getAddress().getHostAddress();
    }

    /*
    **判断channel是否关闭
     */
    public static boolean isClose(Channel channel){
        if(channel==null){
            return true;
        }
        return !channel.isOpen()||!channel.isActive();
    }
}
