package com.company.im.chat.message.user;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.service.LoginService;
import com.company.im.chat.session.IOSession;
import io.netty.channel.Channel;

/*
**用户登录处理
 */
public class UserLoginHandle extends MessageHandle<ResUserLoginPacket> {

    @Override
    protected void action(IOSession session, ResUserLoginPacket packet) {
        Channel channel=session.getChannel();
        User user=session.getUser();
        LoginService loginService= SpringContext.getBean(LoginService.class);
        loginService.LoginValidation(user.getUserName(),
                user.getPassword(), channel);
    }
}
