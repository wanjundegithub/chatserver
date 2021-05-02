package com.company.im.chat.data.dao;

import com.company.im.chat.data.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    boolean addUser(User user);

    boolean deleteUser(int userID);

    boolean updateUser(@Param("user") User user, @Param("userID") int userID);

    int queryUser(String userName);

    int queryMaxUserID();

    User getUserByUserID(int userID);

    User getUserByUserName(String userName);
}
