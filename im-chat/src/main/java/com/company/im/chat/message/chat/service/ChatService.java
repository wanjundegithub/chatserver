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
        logger.info("发送者:"+fromSession.getUser().getUserName()+",发送内容:"+content);
        if(toSession==null){
            logger.error(" 接收者 "+toUserName+" 会话为空");
            return;
        }
        logger.info("接收者:"+toUserName+",发送内容:"+content);
        AbstractPacket packet=new ResChatPacket(toUserName,content);
        fromSession.sendPacket(packet);
        toSession.sendPacket(packet);
    }
}
