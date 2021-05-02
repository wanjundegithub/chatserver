package com.company.im.chat;

import com.company.im.chat.config.ServerConfig;
import com.company.im.chat.context.SpringContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class TestConfig {

    private final Logger logger= LoggerFactory.getLogger(TestConfig.class);

    @Autowired
    private ServerConfig serverConfig;

    @Test
    public void testGetConfig(){
        var ip=serverConfig.getServerIP();
        var serverPort=serverConfig.getSocketPort();
        logger.info("服务器IP:"+ip+",服务器端口:"+serverPort);
    }

    @Test
    public void testGetConfigFromContext(){
        var serverConfig= SpringContext.getServerConfig();
        var ip=serverConfig.getServerIP();
        var serverPort=serverConfig.getSocketPort();
        logger.info("服务器IP:"+ip+",服务器端口:"+serverPort);
    }

}
