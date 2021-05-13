package com.company.im.chat.event;

import com.company.im.chat.common.EventHandler;
import com.company.im.chat.common.EventType;
import com.company.im.chat.context.SpringContext;
import com.company.im.chat.utils.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Spring Bean初始化前注册监听
 */
@Service
public class MessageBeanPostProcessor implements BeanPostProcessor,
        ApplicationContextAware, Ordered {

    @Autowired
    private ListenManager listenManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        try {
            Class<?> clazz=bean.getClass();
            Object listener=bean;
            //反射获取所有bean的Method
            Method[] methods=clazz.getMethods();
            for(var method:methods){
                //获取带有EventHandle注解的方法
                EventHandler eventHandler=method.getAnnotation(EventHandler.class);
                if(eventHandler==null){
                    continue;
                }
                EventType[] eventTypes=eventHandler.value();
                for(var eventType:eventTypes){
                    //将事件及监听对象注入到spring容器中
                    SpringContext.getEventDispatcher().registerListener(eventType,listener);
                    listenManager.addListenHandle(listener,eventType,method);
                }
            }
        }
        catch (Exception e){
            LoggerUtil.error(e.getMessage(),MessageBeanPostProcessor.class);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
