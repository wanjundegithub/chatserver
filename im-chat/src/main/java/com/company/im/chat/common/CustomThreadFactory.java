package com.company.im.chat.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*
*自定义可命名线程工厂
 */
public class CustomThreadFactory implements ThreadFactory {

    private String threadName;

    /*
    **守护线程标志
     */
    private boolean isGuardThread;

    private  final AtomicInteger integerGenerator=new AtomicInteger();

    public CustomThreadFactory(String threadName){
        this(threadName,false);
    }

    public CustomThreadFactory(String threadName,boolean isGuardThread){
        this.threadName=threadName;
        this.isGuardThread=isGuardThread;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread=new Thread(r);
        thread.setDaemon(isGuardThread);
        thread.setName(threadName);
        return thread;
    }

    private String getThreadName(){
        return threadName+"-"+integerGenerator.getAndIncrement();
    }
}
