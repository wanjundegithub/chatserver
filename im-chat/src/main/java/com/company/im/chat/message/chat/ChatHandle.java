package com.company.im.chat.message.chat;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.chat.req.ReqChatPacket;
import com.company.im.chat.session.IOSession;


/*
**聊天处理器
 */
public class ChatHandle extends MessageHandle<ReqChatPacket> {

    @Override
    protected void action(IOSession session, ReqChatPacket packet) {
        SpringContext.getChatService().chat(session,
                packet.getToUserName(), packet.getContent());
    }

    public String test(){
        if(SpringContext.getChatService()==null){
            return "service is null";
        }
        return "hello";
    }
}
