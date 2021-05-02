package com.company.im.chat.serverhandle;

import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketDecoderHandle extends LengthFieldBasedFrameDecoder {

    private final Logger logger= LoggerFactory.getLogger(PacketDecoderHandle.class);

    public PacketDecoderHandle(int maxFrameLength, int lengthFieldOffset,
                               int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame=(ByteBuf) super.decode(ctx,in);
        if(frame.readableBytes()<=0){
            logger.error("解析到帧消息为空");
            return null;
        }
        //获取消息类型
        int packetType=frame.readInt();
        //从消息路由根据消息类型中获取消息实例
        AbstractPacket packet= MessageRouter.Instance.createPacket(packetType);
        //读取bytebuffer来实例化packet
        packet.readBody(frame);
        return packet;
    }
}
