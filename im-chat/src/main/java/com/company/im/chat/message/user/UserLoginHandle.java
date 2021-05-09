package com.company.im.chat.message.user;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.req.ReqUserLoginPacket;
import com.company.im.chat.message.user.service.LoginService;
import com.company.im.chat.session.IOSession;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**用户登录处理
 */
public class UserLoginHandle extends MessageHandle<ReqUserLoginPacket> {

    private static final Logger logger= LoggerFactory.getLogger(UserLoginHandle.class);

    @Override
    protected void action(IOSession session, ReqUserLoginPacket packet) {
        Channel channel=session.getChannel();
        LoginService loginService= SpringContext.getBean(LoginService.class);
        logger.info("userName:"+packet.getUserName()+",password:"+packet.getPassword()
        +" try to login");
        loginService.LoginValidation(packet.getUserName(),
                packet.getPassword(), channel);
    }
}
