package com.company.im.chat.util;

import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.session.IOSession;
import com.company.im.chat.utils.ClassScanner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestMessageRouter {

    private static final Logger logger= LoggerFactory.getLogger(TestMessageRouter.class);

    @Test
    public void testRouter(){
        int packetID= PacketType.ResUserLogin;
        AbstractPacket packet= MessageRouter.Instance.createPacket(packetID);
        if(packet==null){
            logger.error("packet is null");
            return;
        }
        logger.info(packet.getClass().getName());
    }

    @Test
    public void testAddPacket(){

        Map<Integer ,Class<? extends AbstractPacket>> msgPools=new HashMap<>();
        var packetClasses= ClassScanner.getAllSubClass("com.company.message",
                AbstractPacket.class);
        packetClasses.forEach(p->{
            AbstractPacket packet=null;
            try{
                packet= (AbstractPacket) p.getDeclaredConstructor().newInstance();
                if(packet!=null) {
                    logger.info(packet.toString());
                    msgPools.put(packet.getPacketID(),(Class<? extends AbstractPacket>)p);
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Test
    public void addHandle(){
        Map<Integer, MessageHandle> msgHandles=new HashMap<>();
        var handleClasses=ClassScanner.getAllSubClass("com.company.message",
                MessageHandle.class);
        handleClasses.forEach(p->{
            Arrays.stream(p.getDeclaredMethods()).forEach(method ->{
                if(method.getParameterCount()==2){
                    //第一个参数是IOSession类，第二个参数是AbstractPacket的非抽象子类
                    var types=method.getParameterTypes();
                    var firstType=types[0];
                    if(method.getParameterTypes()[0]== IOSession.class){
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
        for(var item:msgHandles.keySet()){
            logger.info(item.toString());
        }
    }
}
