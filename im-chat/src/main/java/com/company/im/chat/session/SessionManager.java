package com.company.im.chat.session;

import com.company.im.chat.common.SessionCloseReason;
import com.company.im.chat.data.model.User;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.user.res.ResUserLogoutPacket;
import com.company.im.chat.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public enum SessionManager {

    Instance;

    private static  final Logger logger= LoggerFactory.getLogger(SessionManager.class);

    /*
     **缓存用户-session键值对
     */
    private ConcurrentHashMap<String,IOSession> userSession=new ConcurrentHashMap<>();

    /*
     **缓存session-用户键值对
     */
    private ConcurrentHashMap<IOSession,String> sessionUser=new ConcurrentHashMap<>();

    /**
     * 发送数据包
     * @param session
     * @param msg
     */
    private void sendPacket(IOSession session,AbstractPacket msg){
        if(session==null){
            logger.error("session is null");
            return;
        }
        if(msg==null){
            logger.error(session+",send message is null");
            return;
        }
        session.sendPacket(msg);
    }

    /**
     * 发送数据包
     * @param userName
     * @param msg
     */
    public void sendPacket(String userName, AbstractPacket msg){
        IOSession session=userSession.get(userName);
        sendPacket(session,msg);
    }

    /**
     * 发送数据包
     * @param channel
     * @param msg
     */
    public void sendPacket(Channel channel, AbstractPacket msg){
        IOSession session= ChannelUtil.getSession(channel);
        sendPacket(session,msg);
    }

    /**
     * 获取session
     * @param userName
     * @return
     */
    public IOSession getSessionByUserName(String userName)
    {
        if(!userSession.containsKey(userName)){
            logger.error(userName +"has not registered");
            return null;
        }
        return userSession.get(userName);
    }

    /**
     * 注册session
     * @param user
     * @param session
     * @return
     */
    public boolean RegisterSession(User user, IOSession session){
        session.setUser(user);
        var userName=user.getUserName();
        if(userSession.containsKey(userName)){
            logger.error("duplicate userName");
            return false;
        }
        if(sessionUser.containsKey(session)){
            logger.error(session.getUser().getUserName()+":duplicate session");
            return false;
        }
        userSession.put(userName,session);
        sessionUser.put(session,userName);
        logger.info(userName+"succeed register session");
        return true;
    }

    /**
     * 注销Session
     * @param channel
     * @param closeReason
     */
    public void UnRegisterSession(Channel channel, SessionCloseReason closeReason){
        IOSession session=ChannelUtil.getSession(channel);
        if(session==null){
            logger.error("session is null,cannot close");
            return;
        }
        String userName=sessionUser.remove(session);
        userSession.remove(userName);
        session.close();
        logger.info("session is closing");
        AbstractPacket packet=new ResUserLogoutPacket(userName+"logout");
        sendPacket(userName,packet);
    }
}
