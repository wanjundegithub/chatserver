package com.company.im.chat.service;

import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.chat.ResChatToOtherPacket;
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
    public void chat(String fromUserName,String toUserName,String content){
        IOSession fromUserSession= SessionManager.Instance.getSessionByUserName(fromUserName);
        if(fromUserSession==null){
            logger.error(fromUserName+" session  is null");
            return;
        }
        IOSession toUserSession=SessionManager.Instance.getSessionByUserName(toUserName);
        if(toUserSession==null){
            logger.error(toUserName+" session is null");
            return;
        }
        AbstractPacket packet=new ResChatToOtherPacket(toUserName,content);
        fromUserSession.sendPacket(packet);
        toUserSession.sendPacket(packet);
    }
}
