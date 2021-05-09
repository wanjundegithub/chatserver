package com.company.im.chat.message.user;


import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.MessageHandle;
import com.company.im.chat.message.user.res.ResUserRegisterPacket;
import com.company.im.chat.session.IOSession;
import io.netty.channel.Channel;

/*
**用户注册处理
 */
public class UserRegisterHandle extends MessageHandle<ResUserRegisterPacket> {

    @Override
    protected void action(IOSession session, ResUserRegisterPacket packet) {
        Channel channel=session.getChannel();
        SpringContext.getUserService().registerUser(channel,session.getUser());
    }
}
