package com.chat.client.view;

import javax.swing.*;

/**
 * 登录界面，其中包括注册功能
 * @author Ranger
 * @create 2019-12-17 21:38
 */
public class LoginFrame {

    public LoginFrame() {
        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
