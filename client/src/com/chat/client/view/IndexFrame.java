package com.chat.client.view;

import com.chat.client.utils.WindowXY;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Ranger
 * @create 2019-12-18 21:55
 */
public class IndexFrame extends PlainDocument {
    private JFrame frame;
    private int limit;  //限制的长度

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

    public IndexFrame() {
        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        double width = WindowXY.getWidth();
        double height = WindowXY.getHeight();
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

        //添加头像
        ImageIcon scul = new ImageIcon("img/head/scul.png");
        JLabel sculLabel = new JLabel();
        sculLabel.setBounds(50, 50, (int) (0.045 * width), (int) (0.078 * height));
        scul.setImage(scul.getImage().getScaledInstance((int) (0.045 * width),
                (int) (0.078 * height), Image.SCALE_DEFAULT));
        sculLabel.setIcon(scul);
        frame.add(sculLabel);

        //添加昵称
        JLabel nameLabel = new JLabel("Ranger");
        nameLabel.setFont(new Font("System", Font.PLAIN, 26));
        nameLabel.setBounds(70 + (int) (0.045 * width), 50, (int) (0.045 * width), 30);
        frame.add(nameLabel);

        //添加签名
        String signStr = "编写签名！";
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

            @Override
            public void mouseExited(MouseEvent e) {
                signField.setEditable(false);
                signField.getCaret().setVisible(false);
            }
        });
        frame.add(signField);

        /**
         * 好友列表
         */
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
        ImageIcon[] headImg = new ImageIcon[10] ;
        for (int i = 0; i < headImg.length; i++) {
            headImg[i] = new ImageIcon("img/head/h" + i + ".jpg");
        }
        JLabel[] headList = new JLabel[10];
        //好友列表
        JLabel[] friendList = new JLabel[10];
        //面板1_0，用来装滚动条，里面存好友列表
        JPanel panel1_0 = new JPanel();
        panel1_0.setPreferredSize(new Dimension((int) (0.21 * width), friendList.length * 70));
        panel1_0.setLayout(null);

        for (int i = 0; i < headList.length; i++) {
            headImg[i].setImage(headImg[i].getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT));
            headList[i] = new JLabel(headImg[i]);
            headList[i].setBounds(0, i * 70, 50, 50);
            panel1_0.add(headList[i]);
        }
        for (int i = 0; i < friendList.length; i++) {
            friendList[i] = new JLabel("罗伯·史塔克" + i);
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
        //群聊列表
        JLabel[] roomList = new JLabel[10];
        JPanel panel2_0 = new JPanel();
        panel2_0.setPreferredSize(new Dimension((int) (0.21 * width), friendList.length * 70));
        panel2_0.setLayout(null);
        for (int i = 0; i < friendList.length; i++) {
            roomList[i] = new JLabel("相亲相爱一家人" + i);
            roomList[i].setFont(new Font("System", Font.PLAIN, 26));
            roomList[i].setBounds(50, i * 70, (int) (0.2 * width), 70);
            panel2_0.add(roomList[i]);
        }
        JScrollPane scrollPane2 = new JScrollPane(panel2_0);
        scrollPane2.setBounds(0, 0, (int) (0.21 * width), (int) (0.55 * height));
        //设置横向滚动条不可见
        panel2.add(scrollPane2);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new IndexFrame();
    }

}
