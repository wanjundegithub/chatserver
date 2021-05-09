package com.company.im.chat;

import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.server.ServerNode;
import com.company.im.chat.server.chat.ChatServer;
import com.company.im.chat.server.http.HttpServer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.company.im.chat.data.dao")
public class ChatServerApplication implements CommandLineRunner {

    private static final Logger logger= LoggerFactory.getLogger(ChatServerApplication.class);

    private List<ServerNode> serverNodes=new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication();
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(ChatServerApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        final ChatServerApplication server=new ChatServerApplication();
        server.start();
        //添加关闭钩子，用于程序退出时释放程序资源
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.stop();
        }));
        logger.info("spring boot start");
    }


    public void start() {
        if(serverNodes==null||serverNodes.size()==0){
            serverNodes.add(new ChatServer());
            serverNodes.add(new HttpServer());
        }
        for(var node:serverNodes){
            node.initServer();
            node.startServer();
        }
        MessageRouter.Instance.toString();
    }

    public void stop() {
        for(var node:serverNodes){
            node.closeServer();
            node.closeServer();
        }
    }


}
