package com.company.im.chat;

import com.company.im.chat.data.dao.FriendDao;
import com.company.im.chat.data.model.Friend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Transactional
public class TestFriendDao {

    private final Logger logger= LoggerFactory.getLogger(TestUserDao.class);

    @Autowired
    private FriendDao friendDao;

    @Test
    @Rollback(value = false)
    public void testQueryFriends(){
        List<Friend> friends=friendDao.queryFriends(1);
        for(var value:friends){
            logger.info(value.toString());
        }
    }
}
