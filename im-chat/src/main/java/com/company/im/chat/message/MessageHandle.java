package com.company.im.chat.message;


import com.company.im.chat.session.IOSession;

/*
**消息处理器
 */
public abstract class MessageHandle <T extends AbstractPacket> {

    protected abstract void action(IOSession session, T packet);

}
