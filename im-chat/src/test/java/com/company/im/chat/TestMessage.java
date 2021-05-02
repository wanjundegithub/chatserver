package com.company.im.chat;

import com.company.im.chat.data.model.Friend;
import com.company.im.chat.helper.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.FriendItemBean;
import com.company.im.chat.message.friend.ResFriendsPacket;
import com.company.im.chat.message.search.ResSearchFriendPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestMessage {

    private static  final Logger logger= LoggerFactory.getLogger(TestMessage.class);

    @Test
    public void testSearchFriendPacket(){
        ResSearchFriendPacket packet=new ResSearchFriendPacket("James","1号");
        ByteBuf buf=Unpooled.buffer(100);
        packet.writeBody(buf);
        //
        ResSearchFriendPacket readPacket=new ResSearchFriendPacket();
        readPacket.readBody(buf);
        logger.info(readPacket.getFriendName()+readPacket.getRemark()+readPacket.getPacketID());
    }

    @Test
    public  void testResFriendPacket(){
        List<FriendItemBean> friendItemBeanList=new ArrayList<>();
        friendItemBeanList.add(new FriendItemBean
                (new Friend("James","12","3",1,"female"),
                        StateHelper.OnLine));
        friendItemBeanList.add(new FriendItemBean
                (new Friend("Bob","kk","4",1,"male")
                        ,StateHelper.OnLine));
        AbstractPacket packet=new ResFriendsPacket(friendItemBeanList);
        ByteBuf buf=Unpooled.buffer(200);
        packet.writeBody(buf);
        //
        ResFriendsPacket readPacket=new ResFriendsPacket();
        readPacket.readBody(buf);
        for(var item:readPacket.getFriends()){
            logger.info(item.toString());
        }
    }

    private Map<Integer, Set<Object>> maps=new HashMap<>();
    @Test
     public void testList(){
        Set<Object> listeners=maps.get(1);
        if(listeners==null){
            listeners=new CopyOnWriteArraySet<>();
            maps.put(1,listeners);
        }
        listeners.add("hello,world");

        for(var key:maps.keySet()){
            var set=maps.get(key);
            for(var o:set){
                logger.info(o.toString());
            }
        }
     }
}
