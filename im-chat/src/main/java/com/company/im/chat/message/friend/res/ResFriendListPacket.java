package com.company.im.chat.message.friend.res;


import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.bean.FriendItemBean;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ResFriendListPacket extends AbstractPacket {

    private List<FriendItemBean> friends;

    public ResFriendListPacket(List<FriendItemBean> friends) {
        this.friends = friends;
    }

    public ResFriendListPacket() {
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
        return PacketType.ResFriendList;
    }

    public List<FriendItemBean> getFriends() {
        return friends;
    }
}
