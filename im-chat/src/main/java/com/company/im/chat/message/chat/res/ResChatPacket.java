package com.company.im.chat.message.chat.res;


import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**聊天消息
 */
public class ResChatPacket extends AbstractPacket {

    private String formUserName;

    private String content;

    public ResChatPacket(String otherUserName, String content) {
        this.formUserName = otherUserName;
        this.content = content;
    }

    public ResChatPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserChat;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf, formUserName);
        writeStringToByte(byteBuf,content);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        formUserName =readByteToString(byteBuf);
        content=readByteToString(byteBuf);
    }

    public String getFormUserName() {
        return formUserName;
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
