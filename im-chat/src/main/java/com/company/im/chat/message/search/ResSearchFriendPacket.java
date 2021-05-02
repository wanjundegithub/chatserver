package com.company.im.chat.message.search;


import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

/*
**搜索好友消息
 */
public class ResSearchFriendPacket extends AbstractPacket {

    private String friendName;

    private String remark;

    public ResSearchFriendPacket(String friendName, String remark) {
        this.friendName = friendName;
        this.remark = remark;
    }

    public ResSearchFriendPacket() {
    }

    @Override
    public int getPacketID() {
        return PacketType.ResSearchFriend;
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        writeStringToByte(byteBuf,friendName);
        writeStringToByte(byteBuf,remark);
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        friendName=readByteToString(byteBuf);
        remark=readByteToString(byteBuf);
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
