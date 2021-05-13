package com.company.im.chat;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.data.model.Friend;
import com.company.im.chat.common.StateHelper;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.message.chat.ChatHandle;
import com.company.im.chat.message.friend.bean.FriendItemBean;
import com.company.im.chat.message.friend.res.ResFriendListPacket;
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
        AbstractPacket packet=new ResFriendListPacket(friendItemBeanList);
        ByteBuf buf=Unpooled.buffer(200);
        packet.writeBody(buf);
        //
        ResFriendListPacket readPacket=new ResFriendListPacket();
        readPacket.readBody(buf);
        for(var item:readPacket.getFriends()){
            logger.info(item.toString());
        }
    }

   @Test
   public void testGetHandle() {
        var handle=(ChatHandle) MessageRouter.Instance.getHandle(PacketType.ReqUserChat);
        logger.info(handle.test());
   }
}
