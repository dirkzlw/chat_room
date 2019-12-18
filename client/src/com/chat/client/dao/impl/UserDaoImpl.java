package com.chat.client.dao.impl;

import com.chat.client.dao.UserDao;
import com.chat.client.po.User;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
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
     * 注册
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {

        HibernateTemplate template = this.getHibernateTemplate();
        //检查用户名是否已被注册
        List<User> userList = (List<User>) template.find("from User where username = ?", user.getUsername());
        if (null != userList && userList.size() > 0) {
            return false;
        }
        template.save(user);
        return true;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public boolean login(User user) {
        HibernateTemplate template = this.getHibernateTemplate();
        //检查用户名是否已被注册
        List<User> userList = (List<User>) template.find("from User where username = ? and password=?",
                user.getUsername(),user.getPassword());
        if (null != userList && userList.size() == 1) {
            return true;
        }
        return false;
    }
}
