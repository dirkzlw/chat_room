package com.chat.client.dao;

import com.chat.client.po.User;

/**
 * @author Ranger
 * @create 2019-12-18 14:45
 */
public interface UserDao {

    boolean checkExist(String username);

    boolean saveUser(User user);

    User findUser(String username, String password);
}
