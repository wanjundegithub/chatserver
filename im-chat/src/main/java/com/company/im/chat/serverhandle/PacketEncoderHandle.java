package com.company.im.chat.serverhandle;

import com.company.im.chat.message.AbstractPacket;
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
            //写入消息体
            abstractPacket.writeBody(byteBuf);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
