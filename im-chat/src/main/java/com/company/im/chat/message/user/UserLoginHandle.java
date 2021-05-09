package com.company.im.chat.message.user;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.req.ReqUserLoginPacket;
import com.company.im.chat.message.user.service.LoginService;
import com.company.im.chat.session.IOSession;
import io.netty.channel.Channel;

/*
**用户登录处理
 */
public class UserLoginHandle extends MessageHandle<ReqUserLoginPacket> {

    @Override
    protected void action(IOSession session, ReqUserLoginPacket packet) {
        Channel channel=session.getChannel();
        User user=session.getUser();
        LoginService loginService= SpringContext.getBean(LoginService.class);
        loginService.LoginValidation(user.getUserName(),
                user.getPassword(), channel);
    }
}
