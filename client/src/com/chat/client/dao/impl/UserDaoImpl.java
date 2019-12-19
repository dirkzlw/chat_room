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
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            template.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
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
