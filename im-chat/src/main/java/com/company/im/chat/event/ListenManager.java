package com.company.im.chat.event;

import com.company.im.chat.common.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
**监听管理
 */
@Component
public class ListenManager {

    private static final Logger logger= LoggerFactory.getLogger(ListenManager.class);

    private Map<String, Method> listenHandles=new ConcurrentHashMap<>();

    private String getKey(Object o, EventType eventType){
        return o.getClass().getName()+"-"+eventType.toString();
    }

    public void addListenHandle(Object o,EventType eventType,Method method){
        var key=getKey(o,eventType);
        listenHandles.put(key,method);
    }

    /*
    **分发执行
     */
    public void fireEvent(Object handle,EventBase event) throws IllegalAccessException {
        var key=getKey(handle,event.getEventType());
        Method method=listenHandles.get(key);
        if(method==null){
            logger.error(key+" method is null");
            return;
        }
        try {
            method.invoke(handle,event);
        }
        catch(InvocationTargetException ite) {
            logger.error(ite.getMessage());
        }
        catch(IllegalAccessException iae){
            logger.error(iae.getMessage());
        }
    }
}
