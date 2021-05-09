package com.company.im.chat.event;


import com.company.im.chat.common.EventType;

/*
**用户抽象定义事件
 */
public abstract class UserEvent extends EventBase {

    private String userName;

    public UserEvent(EventType eventType, String userName) {
        super(eventType);
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }
}
