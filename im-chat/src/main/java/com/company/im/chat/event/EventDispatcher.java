package com.company.im.chat.event;

import com.company.im.chat.common.CustomThreadFactory;
import com.company.im.chat.common.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

/*
**事件分发执行
 */
@Service
public class EventDispatcher {

    private static final Logger logger= LoggerFactory.getLogger(EventDispatcher.class);

    @Autowired
    private ListenManager listenManager;

    /*
    **事件类型与监听器列表的映射
     */
    private Map<EventType, Set<Object>> observers=new HashMap<>();
    /*
    **阻塞队列,异步执行事件
     */
    private LinkedBlockingQueue<EventBase> eventQueues=new LinkedBlockingQueue<>();

    @PostConstruct
    public void eventInit(){
        new CustomThreadFactory("event-dispatcher").newThread(new EventWorker()).start();
    }

    public void triggerEvent(EventBase eventBase) {
        var listeners=observers.get(eventBase.getEventType());
        if(listeners==null||listeners.isEmpty()){
            logger.info("listeners is empty");
            return;
        }
        try {
            for(var o:listeners){
                listenManager.fireEvent(o,eventBase);
            }
        }
        catch (IllegalAccessException illegalAccessException){
            logger.error(illegalAccessException.getMessage());
        }
    }

    public void registerListener(EventType eventType,Object handle){
        var listeners=observers.get(eventType);
        if(listeners==null||listeners.isEmpty()){
            listeners=new CopyOnWriteArraySet<>();
            observers.put(eventType,listeners);
        }
        listeners.add(handle);
    }

    public void fireEvent(EventBase event) {
        if(event==null){
            logger.error("event cannot be null");
            return;
        }
        if(event.isSyncMainThread()){
            triggerEvent(event);
        }
        else {
            try {
                eventQueues.put(event);
            }
            catch (InterruptedException interruptedException){
                logger.error(interruptedException.getMessage());
            }
        }
    }

    private class EventWorker implements Runnable{

        @Override
        public void run() {
            //死循环中执行事件
            while(true){
                try {
                    var event=eventQueues.take();
                    triggerEvent(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
