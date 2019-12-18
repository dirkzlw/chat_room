package com.chat.client.test;

import com.chat.client.po.User;
import com.chat.client.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Ranger
 * @create 2019-12-18 14:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {

    @Autowired
    private UserService userService;

    /**
     * 测试登录
     */
    @Test
    public void login(){
        boolean b = userService.login("dirk", "zhou2");
        if(b){
            System.out.println("登录成功！");
        }else {
            System.out.println("登录失败！");
        }
    }

    /**
     * 测试注册
     */
    @Test
    public void regist(){
        boolean r = userService.regist("dirk", "xxx");
        if(!r){
            System.out.println("用户名已存在！");
        }else {
            System.out.println("注册成功");
        }
    }

}
