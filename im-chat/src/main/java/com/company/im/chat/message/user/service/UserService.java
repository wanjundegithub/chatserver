package com.company.im.chat.message.user.service;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.data.dao.UserDao;
import com.company.im.chat.data.model.User;
import com.company.im.chat.common.SessionCloseReason;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.user.req.ReqUserRegisterPacket;
import com.company.im.chat.message.user.res.ResUserInfoPacket;
import com.company.im.chat.message.user.res.ResUserRegisterPacket;
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
    public boolean isUserOnline(String name){
        return getUserOnlineState(name)==StateHelper.OnLine;
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

    public User getOnlineUser(String name){
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

    /**
     * 新用户注册
     * @param channel
     * @param packet
     */
    public void registerUser(Channel channel,AbstractPacket packet){
        ReqUserRegisterPacket reqUserRegisterPacket=(ReqUserRegisterPacket)packet;
        String userName=reqUserRegisterPacket.getUserName();
        var session= ChannelUtil.getSession(channel);
        ResUserRegisterPacket userRegisterPacket=null;
        if(userDao.getUserByUserName(userName)!=null){
            logger.error("have already same name:"+userName);
            userRegisterPacket=new ResUserRegisterPacket(userName,StateHelper.Action_Failure);
            session.sendPacket(userRegisterPacket);
            return ;
        }
        logger.info(userName+" register success ");
        userRegisterPacket=new ResUserRegisterPacket(userName,StateHelper.Action_Success);
        session.sendPacket(userRegisterPacket);
        userDao.addUser(new User(reqUserRegisterPacket.getUserName(), reqUserRegisterPacket.getPassword(),
                reqUserRegisterPacket.getSex(), reqUserRegisterPacket.getAge(),
                reqUserRegisterPacket.getSignature()));
    }

    /*
    **获取用户信息
     */
    public void onGetUserInfo(User user){
        AbstractPacket packet=new ResUserInfoPacket(user.getUserName(),user.getPassword(),
                user.getSex(),user.getAge(),user.getSignature());
        SessionManager.Instance.sendPacket(user.getUserName(),packet);
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
