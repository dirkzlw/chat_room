package com.chat.client.service;

import org.springframework.stereotype.Service;

/**
 * @author Ranger
 * @create 2019-12-18 14:46
 */
public interface UserService {
    boolean regist(String username, String password);

    boolean login(String dirk, String zhou);
}
