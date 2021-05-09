package com.company.im.chat.message.chat.req;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**用户聊天-请求(客户端发起)
 */
public class ReqChatPacket extends AbstractPacket {

    private String toUserName;

    private String content;

    public ReqChatPacket(String toUserName, String content) {
        this.toUserName = toUserName;
        this.content = content;
    }

    public ReqChatPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ReqUserChat;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,toUserName);
        writeStringToByte(byteBuf,content);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        toUserName=readByteToString(byteBuf);
        content=readByteToString(byteBuf);
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getContent() {
        return content;
    }
}
