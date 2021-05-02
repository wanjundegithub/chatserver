package com.company.im.chat.message.friend;


import com.company.im.chat.helper.PacketType;
import com.company.im.chat.message.AbstractPacket;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ResFriendsPacket extends AbstractPacket {

    private List<FriendItemBean> friends;

    public ResFriendsPacket(List<FriendItemBean> friends) {
        this.friends = friends;
    }

    public ResFriendsPacket() {
    }

    @Override
    public void writeBody(ByteBuf byteBuf) {
        byteBuf.writeInt(friends.size());
        for(var item:friends){
            item.writeBody(byteBuf);
        }
    }

    @Override
    public void readBody(ByteBuf byteBuf) {
        int size=byteBuf.readInt();
        if(friends==null){
            friends=new ArrayList<>();
        }
        for(int i=0;i<size;i++){
            FriendItemBean friendItemBean=new FriendItemBean();
            friendItemBean.readBody(byteBuf);
            friends.add(friendItemBean);
        }
    }

    @Override
    public int getPacketID() {
        return PacketType.ResFriendsInfo;
    }

    public List<FriendItemBean> getFriends() {
        return friends;
    }
}
