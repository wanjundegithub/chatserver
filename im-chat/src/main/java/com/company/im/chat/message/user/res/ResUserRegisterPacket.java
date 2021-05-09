package com.company.im.chat.message.user.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**回应用户注册消息
 */
public class ResUserRegisterPacket extends AbstractPacket {

    private byte resultCode;

    private String message;

    public ResUserRegisterPacket(byte resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ResUserRegisterPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserRegister;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        byteBuf.writeByte(resultCode);
        writeStringToByte(byteBuf,message);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        resultCode=byteBuf.readByte();
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
