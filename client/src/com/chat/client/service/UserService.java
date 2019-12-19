package com.chat.client.service;

import com.chat.client.po.User;

/**
 * @author Ranger
 * @create 2019-12-18 14:46
 */
public interface UserService {
    boolean regist(String username, String password);

    User login(String username, String password);
}
