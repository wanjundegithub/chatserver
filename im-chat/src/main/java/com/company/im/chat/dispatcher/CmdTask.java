package com.company.im.chat.dispatcher;


import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.session.IOSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**用户消息任务处理（多线程处理分发任务）
 */
public class CmdTask extends DispatchTask{

    private static final Logger logger= LoggerFactory.getLogger(CmdTask.class);

    private AbstractPacket packet;

    private IOSession session;

    public CmdTask(int dispatchKey, AbstractPacket packet, IOSession session) {
        this.dispatchKey = dispatchKey;
        this.packet = packet;
        this.session = session;
    }

    @Override
    public void run() {
        try {
            MessageRouter.Instance.execPacket(packet,session);
        }
        catch (Exception e){
            logger.error("消息任务处理"+e.getMessage());
        }
    }
}
