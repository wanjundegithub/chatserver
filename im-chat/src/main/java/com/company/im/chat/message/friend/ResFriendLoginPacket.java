package com.company.im.chat.message.friend;


import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**好友上线消息
 */
public class ResFriendLoginPacket extends AbstractPacket {

    private String friendName;

    public ResFriendLoginPacket(String friendName) {
        this.friendName = friendName;
    }

    public ResFriendLoginPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResFriendLogin;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,friendName);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        friendName=readByteToString(byteBuf);
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
