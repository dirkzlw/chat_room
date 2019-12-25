package com.chat.client.dao.impl;

import com.chat.client.dao.UserDao;
import com.chat.client.po.User;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Ranger
 * @create 2019-12-18 14:45
 */
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    @Resource(name = "sessionFactory")
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);

    }

    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<User> findAllUser() {
        HibernateTemplate template = this.getHibernateTemplate();
        return (List<User>) template.find("from User",null);
    }

    /**
     * 检查用户名是否已被注册
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkExist(String username) {
        HibernateTemplate template = this.getHibernateTemplate();
        //检查用户名是否已被注册
        List<User> userList = (List<User>) template.find("from User where username = ?", username);
        if (null != userList && userList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存用户--用于注册
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            List<User> userList = (List<User>) template.find("from User where username = ?", user.getUsername());
            if (null != userList && userList.size() > 0) {
                System.out.println("用户名已存在,无法保存！");
                return false;
            }
            template.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 更新用户签名
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateSign(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            User user1 = template.get(User.class, user.getUserId());
            user1.setSignStr(user.getSignStr());
            template.update(user1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 更新头像
     */
    @Override
    public boolean updateHeadURL(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            User user1 = template.get(User.class, user.getUserId());
            user1.setHeadUrl(user.getHeadUrl());
            template.update(user1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 更新用户昵称
     */
    @Override
    public boolean updateUsername(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            User user1 = template.get(User.class, user.getUserId());
            user1.setUsername(user.getUsername());
            template.update(user1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUser(String username, String password) {
        HibernateTemplate template = this.getHibernateTemplate();
        List<User> userList = (List<User>) template
                .find("from User where username = ? and password = ?", username, password);
        if (null != userList && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
}
