package com.company.im.chat.message.chat;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.session.IOSession;


/*
**聊天处理器
 */
public class ChatHandle extends MessageHandle<ResChatToOtherPacket> {


    @Override
    protected void action(IOSession session, ResChatToOtherPacket packet) {
        SpringContext.getChatService().chat(session.getUser().getUserName(),packet.getOtherUserName(),
                packet.getContent());
    }
}
