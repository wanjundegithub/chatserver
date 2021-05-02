package com.company.im.chat.service;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.event.UserLoginEvent;
import com.company.im.chat.helper.EventType;
import com.company.im.chat.helper.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.user.ResUserLoginPacket;
import com.company.im.chat.session.IOSession;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger= LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserService userService;

    public void LoginValidation(String userName, String password, Channel channel) {
        User user=userService.validateUser(userName,password);
        IOSession session= ChannelUtil.getSession(channel);
        if(session==null){
            logger.error("cannot get session");
            return;
        }
        if(user==null){
            AbstractPacket packet=new ResUserLoginPacket
                (StateHelper.Action_Failure,userName+" validation fail,check userName or password");
            session.sendPacket(packet);
            return;
        }
        if(!SessionManager.Instance.RegisterSession(user,session)) {
            return;
        }
        AbstractPacket packet=new ResUserLoginPacket(StateHelper.Action_Success,
                userName+"success login");
        session.sendPacket(packet);
        //处理执行
        SpringContext.getEventDispatcher().fireEvent(new UserLoginEvent(EventType.LOGIN,userName));
    }


}
