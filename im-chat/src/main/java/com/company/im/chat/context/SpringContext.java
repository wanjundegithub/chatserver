package com.company.im.chat.context;

import com.company.im.chat.config.ServerConfig;
import com.company.im.chat.dispatcher.MessageDispatcher;
import com.company.im.chat.event.EventDispatcher;
import com.company.im.chat.message.chat.service.ChatService;
import com.company.im.chat.message.friend.service.FriendService;
import com.company.im.chat.message.user.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;

/*
**从spring容器中获取bean对象
 */
@Service
public class SpringContext implements ApplicationContextAware {

    private static  SpringContext self;

    private static ApplicationContext applicationContext;

    private static  ServerConfig serverConfig;

    private static MessageDispatcher messageDispatcher;

    private static UserService userService;

    private static FriendService friendService;

    private static ChatService chatService;

    private static EventDispatcher eventDispatcher;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /*
    **在bean初始化之后进行初始化对象
     */
    @PostConstruct
    public void init(){
        self=this;
    }


    public final static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public final static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }

    public final static <T> Collection<T> getBeanOfTypes(Class<T> clazz){
        return applicationContext.getBeansOfType(clazz).values();
    }

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }

    @Resource
    public  void setServerConfig(ServerConfig serverConfig) {
        SpringContext.serverConfig = serverConfig;
    }

    public static MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    @Resource
    public  void setMessageDispatcher(MessageDispatcher messageDispatcher) {
        SpringContext.messageDispatcher = messageDispatcher;
    }

    public static UserService getUserService() {
        return userService;
    }

    @Resource
    public  void setUserService(UserService userService) {
        SpringContext.userService = userService;
    }

    public static FriendService getFriendService() {
        return friendService;
    }

    @Resource
    public  void setFriendService(FriendService friendService) {
        SpringContext.friendService = friendService;
    }


    public static ChatService getChatService() {
        return chatService;
    }

    @Resource
    public  void setChatService(ChatService chatService) {
        SpringContext.chatService = chatService;
    }

    public static EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    @Resource
    public  void setEventDispatcher(EventDispatcher eventDispatcher) {
        SpringContext.eventDispatcher = eventDispatcher;
    }

}
