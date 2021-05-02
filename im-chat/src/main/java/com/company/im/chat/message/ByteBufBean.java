package com.company.im.chat.message;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/*
**抽象消息基类，主要是读写消息体
 */
public abstract class ByteBufBean {

    private final Logger logger= LoggerFactory.getLogger(ByteBufBean.class);

    public abstract void writeBody(ByteBuf byteBuf);

    public abstract void readBody(ByteBuf byteBuf);

    protected String readByteToString(ByteBuf buf){
        int size=buf.readInt();
        byte[] contents =new byte[size];
        buf.readBytes(contents);
        return new String(contents,StandardCharsets.UTF_8);
    }

    protected void writeStringToByte(ByteBuf buf, String msg){
        byte[] content;
        if(msg.equals("")||msg==null){
            logger.error("消息为空");
            return;
        }
        content=msg.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(content.length);
        buf.writeBytes(content);

    }
}
