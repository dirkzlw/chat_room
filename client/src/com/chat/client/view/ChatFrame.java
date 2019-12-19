package com.chat.client.view;

import com.chat.client.utils.WindowXY;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ranger
 * @create 2019-12-18 16:44
 */
public class ChatFrame {

    private JFrame frame;
    private Timer timer;

    public ChatFrame() {
    }

    public ChatFrame(String title) {

        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Point point = WindowXY.getXY();
        double width = point.getX();
        double height = point.getY();
        Font font = new Font("System", Font.PLAIN, 18);
        frame = new JFrame();
        //设置窗口相关参数
        frame.setTitle(title);
        // 位置及大小
        frame.setBounds((int) (0.2 * width), (int) (0.13 * height), (int) (0.61 * width), (int) (0.7 * height));
        try {
            //设置图标
            frame.setIconImage(ImageIO.read(new FileInputStream(new File("img/surface/logo.png")))); // 设置图标
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setResizable(false); // 窗口大小不可改变
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭窗口程序结束
        frame.setLayout(null); // 布局设置为空
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //窗口关闭，停止定时器
                timer.cancel();
            }
        });

        /**
         * 面板1 用来展示聊天记录
         */
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(Color.white);
        panel1.setBounds(1, 1, (int) (0.78 * 0.6 * width), (int) (0.585 * 0.7 * height));
        panel1.setBorder(BorderFactory.createEtchedBorder());
        // 添加文本框
        JTextArea textShow = new JTextArea();
        textShow.setFont(font);
        textShow.setLineWrap(true); // 自动换行
//        textShow.setEditable(false);
        // 添加滚动条
        JScrollPane scrollPanel1 = new JScrollPane(textShow);
        scrollPanel1.setBounds(10, 2, (int) (0.78 * 0.6 * width) - 10, (int) (0.585 * 0.7 * height) - 5);
        scrollPanel1.setBorder(null); // 去除边框
        panel1.add(scrollPanel1);
        frame.add(panel1);

        /**
         * 面板2 用来发送消息
         */
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(Color.white);
        panel2.setBounds(1, (int) (0.63 * 0.7 * height), (int) (0.78 * 0.6 * width), (int) (0.324 * 0.7 * height));
//        panel2.setBorder(BorderFactory.createEtchedBorder());
        // 添加文本框
        JTextArea textIn = new JTextArea();
        textIn.setFont(font);
        textIn.setLineWrap(true); // 自动换行
        // 添加滚动条
        JScrollPane scrollPanel2 = new JScrollPane(textIn);
        scrollPanel2.setBounds(10, 2, (int) (0.78 * 0.6 * width) - 10, (int) (0.324 * 0.7 * height) - 60);
        scrollPanel2.setBorder(null); // 去除边框
        panel2.add(scrollPanel2);
        // 添加发送按钮
        JButton sendBtn = new JButton("发送");
        sendBtn.setFont(font);
        sendBtn.setBackground(Color.white);
        sendBtn.setBounds((int) (0.78 * 0.6 * width) - 113, (int) (0.324 * 0.7 * height) - 52, 100, 40);
        sendBtn.addActionListener(e -> {

        });
        panel2.add(sendBtn);
        frame.add(panel2);

        //添加斗图的标签
        JLabel tuLabel = new JLabel(new ImageIcon("img/surface/tu.png"));
        tuLabel.setBounds(10, (int) (0.585 * 0.7 * height), 40, (int) (0.045 * 0.7 * height));
        tuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("发送斗图");
            }
        });
        frame.add(tuLabel);

        //添加发送文件的图标
        JLabel fileLabel = new JLabel(new ImageIcon("img/surface/file.png"));
        fileLabel.setBounds((int) ((0.08* 0.7 * height)), (int) (0.585 * 0.7 * height), 40, (int) (0.045 * 0.7 * height));
        fileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println("发送文件");
            }
        });
        frame.add(fileLabel);

        /**
         * 广告位
         */
        //初始化三个图片
        ImageIcon[] ads = new ImageIcon[5];
        for (int i = 0; i < ads.length; i++) {
            ads[i] = new ImageIcon("img/ad/ad" + i + ".jpg");
            ads[i].setImage(ads[i].getImage().getScaledInstance((int) (0.22 * 0.6 * width) + 8,
                    (int) (0.585 * 0.7 * height),
                    Image.SCALE_DEFAULT));
        }
        JLabel adLabel = new JLabel(ads[0]);
        adLabel.setBounds((int) (0.783 * 0.6 * width) - 1, 0, (int) (0.22 * 0.6 * width) + 8, (int) (0.585 * 0.7 * height));
        frame.add(adLabel);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                adLabel.setIcon(ads[(int) (Math.random() * 10) % 5]);
            }
        }, 3000, 5000);

        frame.setVisible(true);

        /**
         * 面板3 展示"群成员"
         */
        JPanel panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBackground(Color.white);
        panel3.setBounds((int) (0.782 * 0.6 * width), (int) (0.588 * 0.7 * height), (int) (0.2285 * 0.6 * width), (int) (0.045 * 0.7 * height));
        //输出条目
        JLabel jlist = new JLabel("群成员(3/5)", JLabel.CENTER);
        jlist.setFont(new Font("System", Font.BOLD, 23));
        jlist.setBounds(0, 1, (int) (0.2285 * 0.6 * width), 35);
        panel3.add(jlist);
        frame.add(panel3);

        /**
         * 面板4 展示群成员列表
         */
        JPanel panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBackground(Color.white);
        panel4.setBounds((int) (0.782 * 0.6 * width), (int) (0.633 * 0.7 * height), (int) (0.2285 * 0.6 * width), (int) (0.324 * 0.7 * height));
        frame.add(panel4);
        JTextArea areaList = new JTextArea();
        areaList.append("[在线] 罗伯史塔克1\n");
        areaList.append("[在线] 罗伯史塔克2\n");
        areaList.append("[在线] 罗伯史塔克3\n");
        areaList.append("[在线] 罗伯史塔克4\n");
        areaList.append("[在线] 罗伯史塔克5\n");
        areaList.append("[离线] 罗伯史塔克6\n");
        areaList.append("[离线] 罗伯史塔克7\n");
        areaList.append("[离线] 罗伯史塔克8\n");
        areaList.append("[离线] 罗伯史塔克0\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.append("[离线] 罗伯史塔克9\n");
        areaList.setFont(new Font("System", Font.PLAIN, 18));
        areaList.setBounds(20, 1, (int) (0.2285 * 0.6 * width) - 20, (int) (0.32 * 0.7 * height));
        // 添加滚动条
        JScrollPane scrollPanel3 = new JScrollPane(areaList);
        scrollPanel3.setBounds(20, 1, (int) (0.2285 * 0.6 * width) - 20, (int) (0.32 * 0.7 * height));
        scrollPanel3.setBorder(null); // 去除边框
        panel4.add(scrollPanel3);
    }

    public static void main(String[] args) {
        new ChatFrame("群聊");
    }
}
