package com.chat.client.view;

import com.chat.client.dao.UserDao;
import com.chat.client.po.User;
import com.chat.client.service.UserService;
import com.chat.client.utils.DataUtils;
import com.chat.client.utils.FtpUtils;
import com.chat.client.utils.WindowXY;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-18 21:55
 */
public class IndexFrame extends PlainDocument {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserService userService = ctx.getBean(UserService.class);

    private JFrame frame;
    private int limit;  //限制的长度
    private ChatFrame chatFrame;

    public IndexFrame(){}

    public IndexFrame(int limit) {
        super(); //调用父类构造
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        //下面的判断条件改为自己想要限制的条件即可，这里为限制输入的长度
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);//调用父类方法
        }
    }

    public IndexFrame(User user) {
        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        double width = WindowXY.getWidth();
        double height = WindowXY.getHeight();

        ImageIcon imageIcon = new ImageIcon("img/surface/backgroud1.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (0.232 * width),(int) (0.78 * height),Image.SCALE_DEFAULT));
        JLabel backgroud = new JLabel(imageIcon);
        backgroud.setOpaque(false);
        backgroud.setBounds(0, 0, (int) (0.232 * width), (int) (0.78 * height));
        frame = new JFrame();
        //设置窗口相关参数
        // 位置及大小
        frame.setBounds((int) (0.74 * width), (int) (0.068 * height), (int) (0.232 * width), (int) (0.78 * height));
        try {
            //设置图标
            frame.setIconImage(ImageIO.read(new FileInputStream(new File("img/surface/logo.png")))); // 设置图标
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setResizable(false); // 窗口大小不可改变
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭窗口程序结束
        frame.setLayout(null); // 布局设置为空

        //添加头像--先从本地读取，未读到根据url下载到本地
        String suf = FtpUtils.readHeadImg(user);
        String sf = FtpUtils.readHeadImg(user);
        ImageIcon headIcon = new ImageIcon("img/head/" + sf);

        JLabel sculLabel = new JLabel();
        sculLabel.setBounds(50, 50, (int) (0.045 * width), (int) (0.078 * height));
        headIcon.setImage(headIcon.getImage().getScaledInstance((int) (0.045 * width),
                (int) (0.078 * height), Image.SCALE_DEFAULT));
        sculLabel.setIcon(headIcon);
        sculLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser("./"); // 文件选择器+默认路径
                fileChooser.setFileFilter(new FileNameExtensionFilter("图片", "png","jpg","bmp","jepg")); // 选择类型
                int resultVal = fileChooser.showOpenDialog(null); // 显示选择器
                File chooseFile = null;
                if (resultVal == fileChooser.APPROVE_OPTION) { // 判断是否确定选择文件
                    chooseFile = fileChooser.getSelectedFile(); // 返回所选择的的文件
                    if(null!=chooseFile){
                        String[] split = chooseFile.getName().split("\\.");
                        String suf = split[1];
                        //进入该页面的时候新建文件：文件名：用户名+时间戳+后缀名
                        String filename = user.getUsername()+"@"+System.currentTimeMillis()+"."+suf;//这个后缀名？
                        /**
                         * 1、将此文件上传到服务器中
                         * 2、放到本地
                         * 3、设置头像
                         */
                        InputStream input = null;
                        try {
                            input = new FileInputStream(chooseFile);
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        boolean flag = FtpUtils.uploadFile("39.107.249.220",
                                21,
                                "html_fs",
                                "html_fs_pwd",
                                "/home/html_fs",
                                "/img",
                                filename,
                                input);

                        if(flag){
                            File file = new File("img/head/" + filename);
                            if(!file.exists()){
                                InputStream is=null;
                                OutputStream out = null;
                                try {
                                    file.createNewFile();
                                    is = new FileInputStream(chooseFile);
                                    out = new FileOutputStream(file);
                                    //写出文件
                                    byte[] bs = new byte[1024];
                                    int len ;
                                    while ((len=is.read(bs))!=-1){
                                        out.write(bs,0,len);
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                } finally {
                                    if(out!=null){
                                        try {
                                            out.close();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    if(input!=null){
                                        try {
                                            input.close();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            }

                        }


                        // url : 39.107.249.220:888/img/logo.png
                        user.setHeadUrl("39.107.249.220:888/img/" + filename);
                        //在后台更新user
                        userService.updateHeanURL(user);
                        //修改头像
                        ImageIcon imageIcon = new ImageIcon("img/head/" + filename);
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (0.045 * width),
                                (int) (0.078 * height), Image.SCALE_DEFAULT));
                        sculLabel.setIcon(imageIcon);
                    }

                } else {
                    System.out.println("没有导入");
                }
            }
        });
        frame.add(sculLabel);

        //添加昵称
        JLabel nameLabel = new JLabel(user.getUsername());
        nameLabel.setFont(new Font("System", Font.PLAIN, 26));
        nameLabel.setBounds(70 + (int) (0.045 * width), 50, (int) (0.13 * width), 30);
        /**
         * 修改昵称
         */
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                String nickName = JOptionPane.showInputDialog("更改昵称");
                if(nickName==null||"".equals(nickName)){
                    System.out.println("取消更改。。");
                    return;
                }
                //检查该用户名是否已经被注册
                boolean flag = userService.checkExist(nickName);
                if(flag){
                    JOptionPane.showMessageDialog(null,"该名字已被注册，请换一个！","提醒",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                //设置用户名
                user.setUsername(nickName);
                userService.updateUsername(user);
                nameLabel.setText(nickName);
            }
        });
        frame.add(nameLabel);

        //添加签名
        String signStr = user.getSignStr();
        JTextField signField = new JTextField();
        signField.setEditable(false);
        signField.setFont(new Font("System", Font.PLAIN, 15));
        signField.setBounds(70 + (int) (0.045 * width), 15 + (int) (0.078 * height), (int) (0.13 * width), 30);
        signField.setDocument(new IndexFrame(16));
        signField.setText(signStr);
        signField.getCaret().setVisible(false);
        signField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signField.setEditable(true);
                signField.getCaret().setVisible(true);
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(signField.isEditable()){
                    signField.setEditable(false);
                    signField.getCaret().setVisible(false);
                    user.setSignStr(signField.getText().trim());
                    System.out.println("userService = " + userService);
                    userService.updateSign(user);

                }
            }
        });
        frame.add(signField);

        /**
         * 好友列表
         */
        //从数据库中获取所有用户
        List<User> userList = userService.findAllUser();
        //遍历删除当前用户
        for (int i = 0; i < userList.size(); i++) {
            if(user.getUsername().equals(userList.get(i).getUsername())){
                userList.remove(i);
                i--;
            }
        }
        //标签
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(20, (int) (0.145 * height), (int) (0.21 * width), (int) (0.55 * height));
        frame.getContentPane().add(tabbedPane);
        //面板1
        JPanel panel1 = new JPanel();
        panel1.setBounds(20, (int) (0.145 * height), (int) (0.21 * width) + 20, (int) (0.55 * height));
        tabbedPane.add("我的好友", panel1);
        panel1.setLayout(null);
        //头像列表
        ImageIcon[] headImg = new ImageIcon[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            suf = FtpUtils.readHeadImg(userList.get(i));
            headImg[i] = new ImageIcon("img/head/" +suf);
        }
        JLabel[] headList = new JLabel[userList.size()];
        //好友列表
        JLabel[] friendList = new JLabel[userList.size()];
        //面板1_0，用来装滚动条，里面存好友列表
        JPanel panel1_0 = new JPanel();
        panel1_0.setPreferredSize(new Dimension((int) (0.21 * width), friendList.length * 70));
        panel1_0.setLayout(null);
        for (int i = 0; i < headList.length; i++) {
            headImg[i].setImage(headImg[i].getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            headList[i] = new JLabel(headImg[i]);
            headList[i].setBounds(0, i * 70, 50, 50);
            panel1_0.add(headList[i]);
        }
        for (int i = 0; i < friendList.length; i++) {
            friendList[i] = new JLabel(userList.get(i).getUsername());
            friendList[i].setFont(new Font("System", Font.PLAIN, 26));
            friendList[i].setBounds(60, i * 70, (int) (0.2 * width), 70);
            panel1_0.add(friendList[i]);
        }
        //滚动条
        JScrollPane scrollPane = new JScrollPane(panel1_0);
        scrollPane.setBounds(0, 0, (int) (0.21 * width), (int) (0.55 * height));
        //设置横向滚动条不可见
        panel1.add(scrollPane);

        //添加群聊
        JPanel panel2 = new JPanel();
        panel2.setBounds(20, (int) (0.145 * height), (int) (0.21 * width) + 20, (int) (0.55 * height));
        tabbedPane.add("群聊", panel2);
        panel2.setLayout(null);
        //群头像
        JPanel panel2_0 = new JPanel();
        ImageIcon qunImg = new ImageIcon("img/head/qun.jpg");
        JLabel qunLabel = new JLabel();
        qunLabel.setBounds(0, -16, (int) (0.045 * width), (int) (0.078 * height));
        qunImg.setImage(qunImg.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT));
        qunLabel.setIcon(qunImg);
        panel2_0.add(qunLabel);
        //群聊列表
        JLabel[] roomList = new JLabel[1];
        panel2_0.setPreferredSize(new Dimension((int) (0.21 * width), roomList.length * 70));
        panel2_0.setLayout(null);
        for (int i = 0; i < roomList.length; i++) {
            roomList[i] = new JLabel("对方正在输入...");
            roomList[i].setFont(new Font("System", Font.PLAIN, 26));
            roomList[i].setBounds(60, i * 70, (int) (0.2 * width), 70);
            panel2_0.add(roomList[i]);
        }
        //打开群聊窗口
        roomList[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(null != chatFrame){
                    if(!chatFrame.getFrame().isVisible()){
                        chatFrame.getFrame().setVisible(true);
                        DataUtils.client.send("@in^A^A^A"+user.getUsername());
                    }
                    chatFrame.getFrame().setAlwaysOnTop(true);
                    chatFrame.getFrame().setAlwaysOnTop(false);
                }else {
                    chatFrame = new ChatFrame("群聊", user, userList);
                }
            }
        });
        JScrollPane scrollPane2 = new JScrollPane(panel2_0);
        scrollPane2.setBounds(0, 0, (int) (0.21 * width), (int) (0.55 * height));
        //设置横向滚动条不可见
        panel2.add(scrollPane2);

        frame.add(backgroud);
        frame.setVisible(true);
    }
}
