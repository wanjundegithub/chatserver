package com.company.im.chat.message.user.res;


import com.company.im.chat.data.model.User;
import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/**
 * 回应用户信息(服务器发送)
 */
public class ResUserInfoPacket extends AbstractPacket {

    private String userName;

    private String password;

    private String sex;

    private int age;

    private String signature;

    @Override
    public int getPacketID() {
        return PacketType.ResUserInfo;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
       writeStringToByte(byteBuf,userName);
       writeStringToByte(byteBuf,password);
       writeStringToByte(byteBuf,sex);
       byteBuf.writeByte(age);
       writeStringToByte(byteBuf,signature);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        userName=readByteToString(byteBuf);
        password=readByteToString(byteBuf);
        sex=readByteToString(byteBuf);
        age=byteBuf.readInt();
        signature=readByteToString(byteBuf);
    }

    public User createUser(){
        return new User(userName,password,sex,age,signature);
    }

    public String getUserName() {
        return userName;
    }
}
