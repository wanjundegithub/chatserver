package com.company.im.chat.message.chat.service;

import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.chat.res.ResChatPacket;
import com.company.im.chat.session.IOSession;
import com.company.im.chat.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static  final Logger logger= LoggerFactory.getLogger(ChatService.class);

    /*
    **双方推送消息
     */
    public void chat(IOSession fromSession,String toUserName,String content){
        IOSession toSession=SessionManager.Instance.getSessionByUserName(toUserName);
        if(toSession==null){
            logger.error(toUserName+" session is null");
            return;
        }
        AbstractPacket packet=new ResChatPacket(toUserName,content);
        fromSession.sendPacket(packet);
        toSession.sendPacket(packet);
    }
}
