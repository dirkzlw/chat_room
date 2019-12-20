package com.chat.client.view;

import com.chat.client.po.User;
import com.chat.client.service.UserService;
import com.chat.client.utils.CodeUtil;
import com.chat.client.utils.GlobalCodeMgr;
import com.chat.client.utils.WindowXY;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 登录界面，其中包括注册功能
 *
 * @author Ranger
 * @create 2019-12-17 21:38
 */
public class LoginFrame {
    private JFrame frame;
    JTextField JT_username1 = new JTextField();
    JPasswordField JT_password1 = new JPasswordField();
    JTextField JT_checkcode = new JTextField();
    JPasswordField JT_verPsw = new JPasswordField();


    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserService userService = ctx.getBean(UserService.class);
    StringBuffer codePicName;
    /**
     * 初始化窗口
     * para:页面长度
     * codePicName:文件名
     */
    public void initFrame(double para) {
        String codePathName = "img/code";
        //设置字体
        Font font = new Font("System", Font.PLAIN, 18);

        int windowWeight = (int)(WindowXY.getWidth() * (0.25));
        int windowHeight = (int) (WindowXY.getHeight() * (0.6));

        JPanel loginPanel = new JPanel();
        JPanel registPanel = new JPanel();
        JLabel JL_username = new JLabel("用户名");
        JLabel JL_password = new JLabel("密  码");

        JTextField JT_username = new JTextField();
        JPasswordField JT_password = new JPasswordField();

        JButton JB_login = new JButton("登录");
        JButton JB_registe = new JButton("注册");

        /**
         * 登录事件
         */
        JB_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = JT_username.getText().trim();
                String password = new String(JT_password.getPassword()).trim();

                User user = new User(username,password,"img/head/h0.jpg","签名非我意！");
                if(userService.login(username,password)!=null){
                    //登录成功
                    System.out.println("登录成功！");
                    frame.setVisible(false);
                    new IndexFrame(user);
                    frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"用户名或密码错误！","登录失败",JOptionPane.PLAIN_MESSAGE);
                }

            }
        });

        //注册
        JLabel JL_title = new JLabel("用户注册");

        JLabel JL_username1 = new JLabel("用户名");
        JLabel JL_password1 = new JLabel("密  码");

        JTextField JT_username1 = new JTextField();
        JPasswordField JT_password1 = new JPasswordField();

        JLabel JL_verPsw = new JLabel("确认密码");
        JPasswordField JT_verPsw = new JPasswordField();

        JLabel JL_checkcode = new JLabel("验证码");
        JTextField JT_checkcode = new JTextField();
        JButton JB_codePic = new JButton();

        JButton JB_abandan = new JButton("放弃");
        JButton JB_registe1 = new JButton("注册");

        loginPanel.setBounds(0,0,windowWeight,windowHeight/3);
        loginPanel.setLayout(null);

        JL_username.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.12), (int)(windowWeight*0.125), (int)(windowHeight*0.03));
        JL_username.setFont(font);
        JL_password.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.16), (int)(windowWeight*0.125), (int)(windowHeight*0.03));
        JL_password.setFont(font);
        JT_username.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.11), (int)(windowWeight*0.27), (int)(windowHeight*0.038));
        JT_username.setFont(font);
        JT_password.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.157), (int)(windowWeight*0.27), (int)(windowHeight*0.038));
        JT_password.setFont(font);
        JB_login.setBounds((int)(windowWeight*0.25),  (int)(windowHeight*0.23), (int)(windowWeight*0.17), (int)(windowHeight*0.053));
        JB_login.setFont(font);
        JB_login.setBackground(Color.white);
        JB_registe.setBounds((int)(windowWeight*0.65),  (int)(windowHeight*0.23), (int)(windowWeight*0.17), (int)(windowHeight*0.053));
        JB_registe.setFont(font);
        JB_registe.setBackground(Color.white);
        /**
         * 改变页面长度
         * para:页面的长度
         */
        JB_registe.addActionListener(new ActionListener() {
            boolean flag = false;
            StringBuffer codePicName;
            @Override
            public void actionPerformed(ActionEvent e) {
                //清空注册页面的信息
                clean();
                flag = !flag;
                if(flag){
                    //验证码生成
                    try {
                        GlobalCodeMgr.getInstance().setCode(CodeUtil.run());
                        System.out.println("验证码为："+GlobalCodeMgr.getInstance().getCode());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    //变长：1、长度改变 2、验证码变化
                    frame.setSize((int) (WindowXY.getWidth() * (0.262)), (int) (WindowXY.getHeight() * (0.6)));
                    JB_codePic.setIcon(new ImageIcon(codePathName+"\\"+codePicName+".jpg"));
                }else{
                    frame.setSize((int) (WindowXY.getWidth() * (0.262)), (int) (WindowXY.getHeight() * (0.27)));
                }
            }
        });

        //设置边框
        registPanel.setBorder(BorderFactory.createTitledBorder("用户注册"));
        registPanel.setLayout(null);
        registPanel.setBounds((int)(windowWeight*0.095), (int)(windowHeight*0.413), (int)(windowWeight*0.8), (int)(windowHeight*0.38));

        JL_username1.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.458), (int)(windowWeight*0.125), (int)(windowHeight*0.03));
        JL_username1.setFont(font);
        JT_username1.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.455), (int)(windowWeight*0.27), (int)(windowHeight*0.038));
        JT_username1.setFont(font);
        JL_password1.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.5), (int)(windowWeight*0.125), (int)(windowHeight*0.03));
        JL_password1.setFont(font);
        JT_password1.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.5), (int)(windowWeight*0.27), (int)(windowHeight*0.038));
        JT_password1.setFont(font);
        JL_verPsw.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.546), (int)(windowWeight*0.2), (int)(windowHeight*0.03));
        JL_verPsw.setFont(font);
        JT_verPsw.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.546), (int)(windowWeight*0.27), (int)(windowHeight*0.038));
        JT_verPsw.setFont(font);
        JL_checkcode.setBounds((int)(windowWeight*0.24), (int)(windowHeight*0.593), (int)(windowWeight*0.2), (int)(windowHeight*0.03));
        JL_checkcode.setFont(font);
        JT_checkcode.setBounds((int)(windowWeight*0.40), (int)(windowHeight*0.593), (int)(windowWeight*0.127), (int)(windowHeight*0.038));
        JT_checkcode.setFont(font);

        //验证码
        JB_codePic.setBounds((int)(windowWeight*0.59), (int)(windowHeight*0.593), (int)(windowWeight*0.23), (int)(windowHeight*0.047));
        /**
         * 点击按钮生成验证码图片
         */
        JB_codePic.addActionListener(new ActionListener() {

            StringBuffer codePicName;
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    GlobalCodeMgr.getInstance().setCode(CodeUtil.run());
                    JB_codePic.setIcon(new ImageIcon(codePathName+"\\"+GlobalCodeMgr.getInstance().getCode()+".jpg"));
                    JT_checkcode.setText("");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });

        JB_abandan.setBounds((int)(windowWeight*0.25), (int)(windowHeight*0.67), (int)(windowWeight*0.17), (int)(windowHeight*0.053));
        JB_abandan.setFont(font);
        JB_abandan.setBackground(Color.white);
        /**
         * 清空文本框内容
         */
        JB_abandan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clean();
            }
        });

        JB_registe1.setBounds((int)(windowWeight*0.65),  (int)(windowHeight*0.67), (int)(windowWeight*0.17), (int)(windowHeight*0.053));
        JB_registe1.setBackground(Color.white);
        JB_registe1.setFont(font);
        /**
         * 注册事件
         */
        JB_registe1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JT_username1.getText().trim();
                String password = new String(JT_password1.getPassword()).trim();
                String verPsw = new String(JT_verPsw.getPassword()).trim();
                String code = JT_checkcode.getText().trim();
                System.out.println("用户名："+username);

                /**
                 * 用户名：4到16位（字母，数字，下划线，减号）
                 * 密码：长度为4-20的所有字符）
                 */
                String reg_username = "^[a-zA-Z0-9_-]{4,16}$";
                String reg_password = "^.{4,20}$";

                if(!username.matches(reg_username)){
                    JOptionPane.showMessageDialog(null,"用户名格式不对！","提醒",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(!password.matches(reg_password)){
                    JOptionPane.showMessageDialog(null,"密码长度至少4位！","提醒",JOptionPane.PLAIN_MESSAGE);
                    return;

                }
                if(!password.equals(verPsw)){
                    System.out.println("两次密码输入不一致");
                    JOptionPane.showMessageDialog(null,"两次密码输入不一致！","提醒",JOptionPane.PLAIN_MESSAGE);

                    JT_password1.setText("");
                    JT_verPsw.setText("");
                    return;
                }
                System.out.println("验证码为"+GlobalCodeMgr.getInstance().getCode());
                //判断验证码是否正确
                if (!(GlobalCodeMgr.getInstance().getCode().toString()).equalsIgnoreCase(code)){
                    System.out.println("验证码不一致");
                    JOptionPane.showMessageDialog(null,"验证码不正确！","提醒",JOptionPane.PLAIN_MESSAGE);
                    try {
                        GlobalCodeMgr.getInstance().setCode(CodeUtil.run());
                        JB_codePic.setIcon(new ImageIcon(codePathName+"\\"+GlobalCodeMgr.getInstance().getCode()+".jpg"));
                        JT_checkcode.setText("");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    return;
                }

                /**
                 * 注册
                 */
                boolean flag = userService.regist(username, password);
                if(flag){
                    JOptionPane.showMessageDialog(null,"注册成功！","提醒",JOptionPane.PLAIN_MESSAGE);
                    //注册成功：回到登录页面，并清空注册页面文本框
                    frame.setSize((int) (WindowXY.getWidth() * (0.262)), (int) (WindowXY.getHeight() * (0.27)));
                    clean();
                }else{
                    /**
                     * 注册失败：用户名重复
                     * 验证码要刷新
                     */
                    JOptionPane.showMessageDialog(null,"用户名重复！","提醒",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        frame.setLayout(null);
        frame.setTitle("QQ");

        try {
            //设置图标
            frame.setIconImage(ImageIO.read(new FileInputStream(new File("img/surface/logo.png")))); // 设置图标
        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         * 添加各组件
         */
        loginPanel.add(JL_username);
        loginPanel.add(JL_password);
        loginPanel.add(JT_username);
        loginPanel.add(JT_password);
        loginPanel.add(JB_login);
        loginPanel.add(JB_registe);
        frame.add(loginPanel);

        frame.add(JL_title);
        frame.add(JL_username1);
        frame.add(JT_username1);
        frame.add(JL_password1);
        frame.add(JT_password1);
        frame.add(JL_verPsw);
        frame.add(JT_verPsw);
        frame.add(JL_checkcode);
        frame.add(JT_checkcode);
        frame.add(JB_codePic);
        frame.add(JB_abandan);
        frame.add(JB_registe1);
        frame.add(registPanel);

        // 设定默认为关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设定窗口大小
        frame.setSize((int) (WindowXY.getWidth() * (0.262)), (int) (WindowXY.getHeight() * (para)));
        //设置窗口居中
        frame.setLocation((int)(WindowXY.getWidth()*0.36),(int)(WindowXY.getHeight()*0.2));
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 清空文本框
     */
    public void clean(){
        JT_username1.setText("");
        JT_password1.setText("");
        JT_verPsw.setText("");
        JT_checkcode.setText("");
    }

    public LoginFrame() {
        frame = new JFrame();
        initFrame(0.27);
        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
