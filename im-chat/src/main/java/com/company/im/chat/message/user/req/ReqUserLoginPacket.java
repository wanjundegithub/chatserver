package com.company.im.chat.message.user.req;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户登录请求（客户端发起）
 */
public class ReqUserLoginPacket extends AbstractPacket {

    private String userName;

    private String password;

    public ReqUserLoginPacket(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public ReqUserLoginPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ReqUserLogin;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,userName);
        writeStringToByte(byteBuf,password);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        userName=readByteToString(byteBuf);
        password=readByteToString(byteBuf);
    }
}
