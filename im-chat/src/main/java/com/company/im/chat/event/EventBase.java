package com.company.im.chat.event;


import com.company.im.chat.helper.EventType;

/*
**事件基类
 */
public abstract class EventBase {

    private long createTime;

    private EventType eventType;

    public EventBase(EventType eventType) {
        this.createTime = System.currentTimeMillis();
        this.eventType = eventType;
    }

    public long getCreateTime() {
        return createTime;
    }


    public EventType getEventType() {
        return eventType;
    }

    public boolean isSyncMainThread(){
        return true;
    }
}
