package com.company.im.chat.dispatcher;


import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.session.IOSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**用户消息任务处理（多线程处理分发任务）
 */
public class CmdTask extends DispatchTask{

    private static final Logger logger= LoggerFactory.getLogger(CmdTask.class);

    private AbstractPacket message;

    private IOSession session;

    public CmdTask(int dispatchKey,AbstractPacket message,IOSession session) {
        this.dispatchKey = dispatchKey;
        this.message = message;
        this.session = session;
    }

    @Override
    public void run() {
        try {

        }
        catch (Exception e){
            logger.error("消息任务处理"+e.getMessage());
        }
    }
}
