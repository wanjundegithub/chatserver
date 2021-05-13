package com.company.im.chat.message.friend.service;

import com.company.im.chat.data.dao.FriendDao;
import com.company.im.chat.data.dao.UserDao;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.data.model.User;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.bean.FriendItemBean;
import com.company.im.chat.message.friend.res.ResFriendListPacket;
import com.company.im.chat.message.friend.res.ResFriendLoginPacket;
import com.company.im.chat.message.friend.res.ResFriendLogoutPacket;
import com.company.im.chat.message.user.service.UserService;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友Service
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    /**
     * 用户登录获取好友列表状态
     * @param user
     */
    public void onGetFriends(User user){
        String userName=user.getUserName();
        var friends=getFriends(userName);
        ResFriendListPacket packet=new ResFriendListPacket(friends);
        SessionManager.Instance.sendPacket(userName,packet);
        //用户上线
        onUserLogin(userName,friends);
    }

    /**
     * 用户上线向所有上线好友发送消息
     * @param userName
     */
    public void onUserLogin(String userName,List<FriendItemBean> friends){
        AbstractPacket packet=new ResFriendLoginPacket(userName);
        for(var item :friends){
            var friendName=item.getFriend().getUserName();
            if(userService.isUserOnline(friendName)){
                SessionManager.Instance.sendPacket(friendName,packet);
            }
        }
    }

    /**
     * 用户下线向所有上线好友发送消息
     * @param userName
     */
    public void onUserLogout(String userName){
        var friends=getFriends(userName);
        AbstractPacket packet=new ResFriendLogoutPacket(userName);
        for(var item :friends){
            var friendName=item.getFriend().getUserName();
            if(userService.isUserOnline(friendName)){
                SessionManager.Instance.sendPacket(friendName,packet);
            }
        }
    }

    /**
     * 获取所有好友信息
     * @param userName
     * @return
     */
    private List<FriendItemBean> getFriends(String userName){
        List<FriendItemBean> results=new ArrayList<>();
        int userID=userDao.queryUser(userName);
        var friends=friendDao.queryFriends(userID);
        if(friends==null||friends.size()==0){
            return new ArrayList<>();
        }
        for(var item:friends){
            byte state= StateHelper.OffLine;
            if(userService.isUserOnline(item.getUserName())){
                state=StateHelper.OnLine;
            }
            results.add(new FriendItemBean(item,state));
        }
        return results;
    }

}
