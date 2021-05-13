package com.company.im.chat.message.user.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**回应用户注册消息
 */
public class ResUserRegisterPacket extends AbstractPacket {

    private String userName;

    private byte result;

    public ResUserRegisterPacket(String userName, byte result) {
        this.userName=userName;
        this.result = result;
    }

    public ResUserRegisterPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserRegister;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,userName);
        byteBuf.writeByte(result);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        userName=readByteToString(byteBuf);
        result=byteBuf.readByte();
    }

    public byte getResult() {
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
