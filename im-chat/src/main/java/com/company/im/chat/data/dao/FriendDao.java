package com.company.im.chat.data.dao;

import com.company.im.chat.data.model.Friend;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao {

    List<Friend> queryFriends(int userID);

}
