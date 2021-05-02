package com.company.im.chat.session;

import com.company.im.chat.data.model.User;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class IOSession {

    private static  final Logger logger= LoggerFactory.getLogger(IOSession.class);

    private User user;

    private String ip;

    private Channel channel;

    private AtomicInteger dispatchGenerator=new AtomicInteger();

    private int dispatchKey;

    private boolean isReConnected;

    public IOSession(){

    }

    public IOSession(Channel channel){
        this.channel=channel;
        this.ip= ChannelUtil.getRemoteIp(channel);
        this.dispatchKey=dispatchGenerator.getAndIncrement();
    }

    /*
    *通道发送消息
     */
    public  void sendPacket(AbstractPacket msg){
        try {
            if(channel!=null&&msg!=null) {
                channel.writeAndFlush(msg);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public void close(){
        if(channel==null){
            return;
        }
        if(!channel.isActive()){
            logger.info(channel+" already closed");
            return;
        }
        channel.close();
        logger.info(channel+" is closing");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public int getDispatchKey() {
        return dispatchKey;
    }

    public void setDispatchKey(int dispatchKey) {
        this.dispatchKey = dispatchKey;
    }

    public boolean isReConnected() {
        return isReConnected;
    }

    public void setReConnected(boolean reConnected) {
        isReConnected = reConnected;
    }

}
