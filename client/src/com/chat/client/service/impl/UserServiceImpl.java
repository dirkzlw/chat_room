package com.chat.client.service.impl;

import com.chat.client.dao.UserDao;
import com.chat.client.po.User;
import com.chat.client.service.UserService;
import com.chat.client.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-18 14:46
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private String headUrl = "39.107.249.220:888/img/scul.png";
    private String signStr = "留下您的个性签名！";

    @Override
    public boolean regist(String username, String password) {
        if (!userDao.checkExist(username)) {
            return userDao.saveUser(new User(username,
                    MD5Utils.md5(password),
                    headUrl, signStr));
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        return userDao.findUser(username,MD5Utils.md5(password));
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void updateSign(User user) {
        userDao.updateSign(user);
    }

    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }
}
