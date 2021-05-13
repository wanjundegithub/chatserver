package com.company.im.chat.serverhandle;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.res.ResFriendListPacket;
import com.company.im.chat.message.user.res.ResUserLoginPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketEncoderHandle extends MessageToByteEncoder<AbstractPacket> {

    private final Logger logger= LoggerFactory.getLogger(PacketEncoderHandle.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          AbstractPacket abstractPacket, ByteBuf byteBuf)  {
        try {
            //写入消息标识
            byteBuf.writeInt(abstractPacket.getPacketID());
            logger.info("message type is :"+abstractPacket.getPacketID()
            +",message is "+abstractPacket.getClass().getName());
            //test
            if(abstractPacket.getPacketID()== PacketType.ResFriendList){
                var packet=(ResFriendListPacket)abstractPacket;
                logger.info("friend size is: "+packet.getFriends().size());
            }
            //写入消息体
            abstractPacket.writeBody(byteBuf);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
