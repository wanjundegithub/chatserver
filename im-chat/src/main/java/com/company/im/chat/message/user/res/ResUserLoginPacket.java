package com.company.im.chat.message.user.res;


import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户登录消息
 */
public class ResUserLoginPacket extends AbstractPacket {

    private byte resultCode;

    private String message;

    public ResUserLoginPacket(byte resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ResUserLoginPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserLogin;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        byteBuf.writeByte(resultCode);
        writeStringToByte(byteBuf,message);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        resultCode =byteBuf.readByte();
        message=readByteToString(byteBuf);
    }

    public byte getResultCode() {
        return resultCode;
    }

    public void setResultCode(byte resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
