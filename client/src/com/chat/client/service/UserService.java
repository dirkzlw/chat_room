package com.chat.client.service;

import com.chat.client.po.User;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-18 14:46
 */
public interface UserService {
    boolean regist(String username, String password);

    User login(String username, String password);

    void saveUser(User user);

    void updateSign(User user);

    List<User> findAllUser();
}
