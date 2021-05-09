package com.company.im.chat.message.user;

import com.company.im.chat.common.EventHandler;
import com.company.im.chat.common.EventType;
import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.event.UserLoginEvent;
import com.company.im.chat.message.user.service.UserService;
import com.company.im.chat.utils.ChannelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {

    private static final Logger logger= LoggerFactory.getLogger(UserFacade.class);

    @Autowired
    private UserService userService;

    @EventHandler(value={EventType.LOGIN})
    public void onUserLogin(UserLoginEvent userLoginEvent){
        String userName=userLoginEvent.getUserName();
        User user= SpringContext.getUserService().getOnlineUser(userName);
        if(user==null){
            logger.error(userName+" fail to online");
            return;
        }
        userService.onGetUserInfo(user);
    }

}
