package com.company.im.chat.message.friend;

import com.company.im.chat.common.EventHandler;
import com.company.im.chat.common.EventType;
import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.model.User;
import com.company.im.chat.event.UserLoginEvent;
import com.company.im.chat.message.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外观模式（Facade Pattern）隐藏系统的复杂性，并向客户端提供了一个客户端可以访问系统的接口
 */
@Service
public class FriendFacade {

    @Autowired
    private FriendService friendService;

    /*
    **处理用户登录事件
     */
    @EventHandler(value={EventType.LOGIN})
    public void onUserLogin(UserLoginEvent userLoginEvent){
        String userName=userLoginEvent.getUserName();
        User user= SpringContext.getUserService().getOnlineUser(userName);
        if(user!=null){
            friendService.onGetFriends(user);
        }
    }

}
