package com.company.im.chat.dispatcher;

import com.company.im.chat.common.CustomThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class MessageDispatcher {

    private static  final Logger logger= LoggerFactory.getLogger(MessageDispatcher.class);

    private int coreProcessorsNumber=Runtime.getRuntime().availableProcessors();

    /*
    **设计多个线程池处理不同用户任务
     */
    private ExecutorService[] executorServices=new ExecutorService[coreProcessorsNumber];

    @PostConstruct
    public void init(){
        for(int i=0;i<coreProcessorsNumber;i++){
            executorServices[i]=Executors.newSingleThreadExecutor(
                    new CustomThreadFactory("MessageDispatcher"));
        }
    }

    /*
    **线程池提交用户任务
     */
    public Future submitTask(DispatchTask dispatchTask){
        int dispatchKey=dispatchTask.getDispatchKey();
        //取模运算决定哪一个线程池执行任务
        int index=dispatchKey%coreProcessorsNumber;
        return executorServices[index].submit(dispatchTask);
    }
}
