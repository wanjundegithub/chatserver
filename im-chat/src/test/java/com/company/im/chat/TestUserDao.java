package com.company.im.chat;

import com.company.im.chat.data.dao.UserDao;
import com.company.im.chat.data.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Transactional
public class TestUserDao  {

    private final Logger logger= LoggerFactory.getLogger(TestUserDao.class);

    @Autowired
    private UserDao userDao;

    @Value("Bob")
    private String userName;

    @Test
    public void testQueryUser() throws Exception {
        int userID=userDao.queryUser(userName);
        logger.info("用户ID:"+String.valueOf(userID));
    }

    @Test
    @Rollback(value = false)
    public void testAddUser(){
        User user=new User("Flip","123","male",18,"success");
        boolean flag=userDao.addUser(user);
        logger.info("添加用户操作:"+flag);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteUser(){
        String name="Flip";
        int userID=userDao.queryUser(name);
        boolean flag=userDao.deleteUser(userID);
        logger.info("删除用户操作:"+flag);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateUser(){
        int userID=6;
        User oldUser=userDao.getUserByUserID(userID);
        oldUser.setSignature("天空之泪");
        boolean flag=userDao.updateUser(oldUser,userID);
        logger.info("更新用户操作:"+flag);
    }

    @Test
    public void testQueryMaxID(){
        int userID=userDao.queryMaxUserID();
        logger.info("查询最大用户ID:"+userID);
    }

    @Test
    public void testGetUser(){
        User user=userDao.getUserByUserID(3);
        logger.info(user.toString());
    }

    @Test
    public void testGetUserByName(){
        User user=userDao.getUserByUserName("Smith");
        if(user!=null){
            logger.info(user.toString());
        }
    }

}
