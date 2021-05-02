package com.company.im.chat;

import com.company.im.chat.server.ChatServer;
import com.company.im.chat.server.ServerNode;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.company.im.chat.data.dao")
public class ChatServerApplication implements CommandLineRunner {

    private static final Logger logger= LoggerFactory.getLogger(ChatServerApplication.class);

    private ServerNode serverNode;

    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication();
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        //添加关闭钩子，用于程序退出时释放程序资源
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            stop();
        }));
        logger.info("spring boot start");
       start();
    }

    public void start(){
        if(serverNode==null){
            serverNode=new ChatServer();
        }
        serverNode.initServer();
        serverNode.startServer();
    }

    public void stop(){
        if(serverNode!=null){
            serverNode.closeServer();
        }
    }
}
