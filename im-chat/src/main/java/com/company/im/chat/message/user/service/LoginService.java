package com.company.im.chat.message.user.service;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.event.UserLoginEvent;
import com.company.im.chat.common.EventType;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.user.res.ResUserLoginPacket;
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

    /**
     * 用户登录验证
     * @param userName
     * @param password
     * @param channel
     */
    public void LoginValidation(String userName, String password, Channel channel) {
        User user=userService.validateUser(userName,password);
        IOSession session= ChannelUtil.getSession(channel);
        if(session==null){
            logger.error("cannot get session");
            return;
        }
        if(user==null){
            ResUserLoginPacket packet=new ResUserLoginPacket(StateHelper.Action_Failure,
                    userName+" validation fail,check userName or password");
            logger.info("login message is: "+packet.getMessage());
            session.sendPacket(packet);
            return;
        }
        onSucceedLoginHandle(user,session);
    }

    /**
     * 验证通过后处理
     * @param user
     * @param session
     */
    private void onSucceedLoginHandle(User user,IOSession session){
        var userName=user.getUserName();
        userService.addOnlineUser(userName,user);
        //注册user对应session
        if(!SessionManager.Instance.RegisterSession(user,session)) {
            return;
        }
        ResUserLoginPacket packet=new ResUserLoginPacket(StateHelper.Action_Success,
                userName+" success login");
        session.sendPacket(packet);
        logger.info("login success send message is "+packet.getMessage());
        //处理相关的登录事件
        SpringContext.getEventDispatcher().fireEvent(
                new UserLoginEvent(EventType.LOGIN,userName));
    }


}
