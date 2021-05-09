package com.company.im.chat.event;


import com.company.im.chat.common.EventType;

/*
**用户登录事件
 */
public class UserLoginEvent extends UserEvent{

    public UserLoginEvent(EventType eventType, String userName) {
        super(eventType, userName);
    }
}
