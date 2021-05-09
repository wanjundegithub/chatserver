package com.company.im.chat.message.user.res;


import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户登出消息
 */
public class ResUserLogoutPacket extends AbstractPacket {

    private String message;

    public ResUserLogoutPacket(String message) {
        this.message = message;
    }

    public ResUserLogoutPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserLogout;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,message);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        message=readByteToString(byteBuf);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
