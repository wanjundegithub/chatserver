package com.company.im.chat.service;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.dao.UserDao;
import com.company.im.chat.data.model.User;
import com.company.im.chat.helper.SessionCloseReason;
import com.company.im.chat.helper.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.user.ResUserRegisterPacket;
import com.company.im.chat.session.IOSession;
import com.company.im.chat.session.SessionManager;
import com.company.im.chat.utils.ChannelUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
**用户服务：用户登录，登出，创建新用户
 */
@Service
public class UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    /*
    **最近登录过的所有用户
     */
    private ConcurrentHashMap<String, User> loginUsers=new ConcurrentHashMap<>(1000);

    /*
    **在线用户
     */
    private Set<String> onlineUsers=new HashSet<>();

    /*
    **添加登录用户
     */
    public void addOnlineUser(String  name,User user){
        loginUsers.put(name,user);
        onlineUsers.add(name);
    }

    /*
    **移除登录用户
     */
    public void removeFromOnlineUsers(String name){
        onlineUsers.add(name);
    }

    /*
    判断用户是否在线
     */
    public byte getUserOnlineState(String name){
        if(onlineUsers.contains(name))
        {
            return StateHelper.OnLine;
        }
        return StateHelper.OffLine;
    }

    private User getOnlineUser(String name){
        if(!loginUsers.containsKey(name)){
            logger.error("在线登录列表中不包含该用户名："+name);
            return null;
        }
        return  loginUsers.get(name);
    }

    /*
    **登录验证用户
     */
    public User validateUser(String name,String password){
        User user=userDao.getUserByUserName(name);
        if(user==null){
            logger.error("查询用户不存在，请检查用户名:"+name);
            return null;
        }
        if(!user.getPassword().equals(password)){
            logger.error("用户名或密码不正确,用户名："+name+",密码:"+password);
            return null;
        }
        return user;
    }

    /*
    **注册新用户
     */
    public boolean registerUser(Channel channel,User user){
        var name=user.getUserName();
        var session= ChannelUtil.getSession(channel);
        ResUserRegisterPacket userRegisterPacket=null;
        if(userDao.getUserByUserName(name)!=null){
            logger.error("已存在相同用户名:"+name);
            userRegisterPacket=new ResUserRegisterPacket(StateHelper.Action_Failure,
                    "已存在相同用户名:"+name);
            session.sendPacket(userRegisterPacket);
            return false;
        }
        userRegisterPacket=new ResUserRegisterPacket(StateHelper.Action_Success, name+"用户注册成功");
        userDao.addUser(user);
        return false;
    }

    /*
    **更新用户
     */
    public boolean updateUser(Channel channel,User user){
//        if(!onlineUsers.contains(user.getUserName())){
//            logger.error(user.getUserName()+" cannot update");
//            return false;
//        }
//        AbstractPacket packet=new ResUserUpdatePacket(user.getUserName(),user.getPassword(),
//                user.getSex(),user.getAge(),user.getSignature());
//        var id=userDao.queryUser(user.getUserName());
//        userDao.updateUser(user,id);
//        SessionManager.Instance.sendPacket(user.getUserName(),packet);
//        logger.info(user.getUserName()+" update success");
        return true;
    }

    /*
    **用户登出
     */
    public void logout(Channel channel, SessionCloseReason reason){
        IOSession session=ChannelUtil.getSession(channel);
        var userName=session.getUser().getUserName();
        //在线用户列表移除下线用户
        SpringContext.getUserService().removeFromOnlineUsers(userName);
        //好友列表中处理下线用户
        SpringContext.getFriendService().onUserLogout(userName);
        SessionManager.Instance.UnRegisterSession(channel, reason);
    }


}
