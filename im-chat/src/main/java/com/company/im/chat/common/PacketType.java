package com.company.im.chat.common;

/*
 **消息类型
 */
public  class PacketType {

    /*
     **请求-用户注册
     */
    public static int ReqUserRegister=1_000;

    /*
     **回应-用户注册
     */
    public static int ResUserRegister=1_001;

    /*
     **请求-用户登录
     */
    public static  int ReqUserLogin=2_000;

    /*
     **回应-用户注册
     */
    public static  int ResUserLogin=2_001;

    /*
     **请求-用户更新
     */
    public static int ReqUserUpdate=3_000;

    /*
     **回应-用户更新
     */
    public static int ResUserUpdate=3_001;

    /*
     **请求-用户登出
     */
    public static int ReqUserLogout=4_000;

    /*
     **回应-用户登出
     */
    public static int ResUserLogout=4_001;

    /*
     **请求-用户聊天
     */
    public static int ReqUserChat=5_000;

    /*
     **回应-用户聊天
     */
    public static int ResUserChat=5_001;

    /*
     **请求-搜索好友
     */
    public static int ReqSearchFriend=6_000;

    /*
     **回应-搜索好友
     */
    public static int ResSearchFriend=6_001;

    /*
     **请求-好友登录
     */
    public static int ResFriendLogin=7_000;

    /*
     **回应-好友登录
     */
    public static int ResFriendLogout=7_001;

    /*
     **请求-用户信息
     */
    public static int ReqUserInfo=8_000;

    /*
     **回应-用户信息
     */
    public static int ResUserInfo=8_001;

    /*
     **回应-好友
     */
    public static int ResFriendsInfo=9_001;

    /*
     **回应-好友列表
     */
    public static int ResFriendList=9_002;

}
