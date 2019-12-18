package com.chat.client.service.impl;

import com.chat.client.dao.UserDao;
import com.chat.client.po.User;
import com.chat.client.service.UserService;
import com.chat.client.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ranger
 * @create 2019-12-18 14:46
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean regist(String username, String password) {
        User user = new User(username, MD5Utils.md5(password));
        return userDao.regist(user);
    }

    @Override
    public boolean login(String username, String password) {
        User user = new User(username, MD5Utils.md5(password));
        return userDao.login(user);
    }
}
