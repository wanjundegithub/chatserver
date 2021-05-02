package com.company.im.chat.dispatcher;
/*
任务处理基类,分发索引用来标记任务
 */
public abstract class DispatchTask implements Runnable {

    protected int dispatchKey;

    public int getDispatchKey() {
        return dispatchKey;
    }
}
