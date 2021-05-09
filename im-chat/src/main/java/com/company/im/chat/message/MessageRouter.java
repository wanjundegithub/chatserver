package com.company.im.chat.message;

import com.company.im.chat.session.IOSession;
import com.company.im.chat.utils.ChannelUtil;
import com.company.im.chat.utils.ClassScannerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息路由，由消息标识中获取消息实体，消息处理器
 */
public enum MessageRouter {

    Instance;

    private   final Logger logger= LoggerFactory.getLogger(MessageRouter.class);

    private Map<Integer ,Class<? extends AbstractPacket>> msgPools=new HashMap<>();

    private Map<Integer, MessageHandle> msgHandles=new HashMap<>();

    /*
    **实例化msgPools,msgHandles
     */
    MessageRouter(){
        var packetClasses= ClassScannerUtil.getAllSubClass(
                "com.company.im.chat.message", AbstractPacket.class);
        packetClasses.forEach(p->{
            AbstractPacket packet=null;
            try{
                packet= (AbstractPacket) p.getDeclaredConstructor().newInstance();
                msgPools.put(packet.getPacketID(),(Class<? extends AbstractPacket>)p);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        var handleClasses= ClassScannerUtil.getAllSubClass(
                "com.company.im.chat.message", MessageHandle.class);
        handleClasses.forEach(p->{
            Arrays.stream(p.getDeclaredMethods()).forEach(method ->{
                if(method.getParameterCount()==2){
                    //第一个参数是IOSession类，第二个参数是AbstractPacket的非抽象子类
                    if(method.getParameterTypes()[0]==IOSession.class){
                        Class<?> secondParameterType= method.getParameterTypes()[1];
                        if(AbstractPacket.class.isAssignableFrom(secondParameterType)&&
                                !Modifier.isAbstract(secondParameterType.getModifiers())){
                            MessageHandle msgHandle=null;
                            try{
                                msgHandle= (MessageHandle) p.getDeclaredConstructor().newInstance();
                                AbstractPacket packet=(AbstractPacket)secondParameterType.
                                        getDeclaredConstructor().newInstance();
                                var oldHandle=msgHandles.put(packet.getPacketID(),
                                        msgHandle);
                                if(oldHandle!=null){
                                    logger.error("repeat register message router");
                                    System.exit(1);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        });
    }

    /*
    **处理消息
     */
    public void execPacket(AbstractPacket packet, IOSession session) throws InstantiationException {
        if(packet==null){
            logger.error("message is null");
            return;
        }
        if(session==null|| ChannelUtil.isClose(session.getChannel())){
            logger.error("session is null or channel is close");
            return;
        }
        var packetType=packet.getPacketID();
        try {
            var messageHandle=msgHandles.get(packetType);
            messageHandle.action(session,packet);
        }
        catch (Exception e){
            logger.error("异常:"+e.getMessage());
        }
    }

    /*
    **根据消息标识创建消息实例
     */
    public AbstractPacket createPacket(int packetID){
        var clazz=msgPools.get(packetID);
        if(clazz==null){
            logger.error(packetID+" message is null");
            return null;
        }
        AbstractPacket packet=null;
        try {
            packet=clazz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return  packet;
    }
}
