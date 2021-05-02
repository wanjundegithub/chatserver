package com.company.im.chat.message;

/*
**抽象消息，增加消息标识
 */
public abstract class AbstractPacket extends ByteBufBean{

   public abstract int getPacketID();

}
