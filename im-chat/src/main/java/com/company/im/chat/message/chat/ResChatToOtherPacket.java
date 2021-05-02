package com.company.im.chat.message.chat;


import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**聊天消息
 */
public class ResChatToOtherPacket extends AbstractPacket {

    private String otherUserName;

    private String content;

    public ResChatToOtherPacket(String otherUserName, String content) {
        this.otherUserName = otherUserName;
        this.content = content;
    }

    public ResChatToOtherPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResUserChat;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,otherUserName);
        writeStringToByte(byteBuf,content);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        otherUserName=readByteToString(byteBuf);
        content=readByteToString(byteBuf);
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
