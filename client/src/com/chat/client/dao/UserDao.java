package com.chat.client.dao;

import com.chat.client.po.User;

/**
 * @author Ranger
 * @create 2019-12-18 14:45
 */
public interface UserDao {

    boolean regist(User user);

    boolean login(User user);
}
