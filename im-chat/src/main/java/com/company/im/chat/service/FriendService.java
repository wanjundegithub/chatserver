package com.company.im.chat.service;

import com.company.im.chat.data.dao.FriendDao;
import com.company.im.chat.data.dao.UserDao;
import com.company.im.chat.data.model.Friend;
import com.company.im.chat.helper.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.FriendItemBean;
import com.company.im.chat.message.friend.ResFriendLoginPacket;
import com.company.im.chat.message.friend.ResFriendLogoutPacket;
import com.company.im.chat.message.friend.ResFriendsPacket;
import com.company.im.chat.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    private static  final Logger logger= LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    public List<FriendItemBean> getAllFriends(String userName){
        var id=userDao.queryUser(userName);
        List<Friend> friends=friendDao.queryFriends(id);
        if(friends==null||friends.size()==0){
            return null;
        }
        List<FriendItemBean> friendItemBeanList=new ArrayList<>();
        for(var item :friends){
            if(item.getUserName().equals(userName)){
                continue;
            }
            byte onLineState=userService.getUserOnlineState(item.getUserName());
            friendItemBeanList.add(new FriendItemBean(item,onLineState));
        }
        return friendItemBeanList;
    }

    public void onUserLogout(String userName) {
        var friends=getAllFriends(userName);
        if(friends==null||friends.size()==0){
            return;
        }
        for(var item :friends){
            var name=item.getFriend().getUserName();
            if(userService.getUserOnlineState(name)== StateHelper.OffLine) {
                AbstractPacket packet = new ResFriendLogoutPacket(name);
                SessionManager.Instance.sendPacket(name, packet);
            }
        }
    }

    public void onUserLogin(String userName){
        var friends=getAllFriends(userName);
        if(friends==null||friends.size()==0){
            return;
        }
        for(var item :friends){
            var name=item.getFriend().getUserName();
            if(userService.getUserOnlineState(name)== StateHelper.OnLine) {
                AbstractPacket packet = new ResFriendLoginPacket(name);
                SessionManager.Instance.sendPacket(name, packet);
            }
        }
    }

    public void updateUserFriends(String userName){
        var friends=getAllFriends(userName);
        if(friends==null||friends.size()==0){
            return;
        }
        AbstractPacket packet=new ResFriendsPacket(friends);
        SessionManager.Instance.sendPacket(userName,packet);
        onUserLogin(userName);
    }
}
