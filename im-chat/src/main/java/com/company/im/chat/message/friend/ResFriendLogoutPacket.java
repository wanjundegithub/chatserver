package com.company.im.chat.message.friend;


import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**好友下线消息
 */
public class ResFriendLogoutPacket extends AbstractPacket {

    private String friendName;

    public ResFriendLogoutPacket(String friendName) {
        this.friendName = friendName;
    }

    public ResFriendLogoutPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResFriendLogout;
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
