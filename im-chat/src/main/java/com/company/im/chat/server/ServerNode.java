package com.company.im.chat.server;

public interface ServerNode {
    /*
    **初始化服务器
     */
    void initServer();

    /*
    **启动服务器
     */
    void startServer();

    /*
    **关闭服务器
     */
    void closeServer();
}
