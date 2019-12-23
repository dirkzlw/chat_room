package com.chat.client.dao;

import com.chat.client.po.User;

import java.util.List;


/**
 * @author Ranger
 * @create 2019-12-18 14:45
 */
public interface UserDao {

    List<User> findAllUser();

    boolean checkExist(String username);

    boolean saveUser(User user);

    boolean updateSign(User user);

    User findUser(String username, String password);
}
