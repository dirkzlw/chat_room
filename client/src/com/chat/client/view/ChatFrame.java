package com.chat.client.view;

import com.chat.client.client.Client;
import com.chat.client.client.ClientThread;
import com.chat.client.po.User;
import com.chat.client.utils.DataUtils;
import com.chat.client.utils.FtpUtils;
import com.chat.client.utils.WindowXY;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ranger
 * @create 2019-12-18 16:44
 */
public class ChatFrame {

    @Getter
    private JFrame frame;
    private TuFrame tuFrame;
    private FlistFrame flistFrame;
    private Timer timer;
    private JTextPane textPane = new JTextPane();
    @Getter
    private boolean close = false;
    private JLabel jlist = new JLabel("群成员", JLabel.CENTER);
    private JTextArea memberList = new JTextArea();

    public ChatFrame() {
    }

    public ChatFrame(String title, User user, List<User> userList) {
        if(!isClose()){
            //初始化Client
            DataUtils.client.setTextPane(textPane);
            DataUtils.client.setJlist(jlist);
            DataUtils.client.setMemberList(memberList);
            DataUtils.client.setUserList(userList);
            DataUtils.client.send(user.getUsername());
            new ClientThread(DataUtils.client.getServer(), textPane, jlist, memberList, userList).start();
        }
        // 设置窗口外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        double width = WindowXY.getWidth();
        double height = WindowXY.getHeight();
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
                //停止client
                DataUtils.client.send("@exit^A^A^A");
                // 将当前对象置空
                frame.setVisible(false);
                close = true;
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
        textPane.setFont(font);
        textPane.setEditable(false);
        // 以下3行 实现滚动条保持在最下部
        DefaultCaret caret = (DefaultCaret) textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textPane.setSelectionStart(textPane.getText().length());
        // 添加滚动条
        JScrollPane scrollPanel1 = new JScrollPane(textPane);
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
            String inLine = textIn.getText();
            DataUtils.client.send(inLine);
            textIn.setText("");
        });
        panel2.add(sendBtn);
        frame.add(panel2);

        //添加斗图的标签
        JLabel tuLabel = new JLabel(new ImageIcon("img/surface/tu.png"));
        tuLabel.setBounds(10, (int) (0.585 * 0.7 * height), 40, (int) (0.045 * 0.7 * height));
        tuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null == tuFrame || null == tuFrame.getFrame()) {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    tuFrame = new TuFrame((int) p.getX(), (int) p.getY() - 130);
                }
            }
        });
        frame.add(tuLabel);

        //添加发送文件的图标
        JLabel fileLabel = new JLabel(new ImageIcon("img/surface/file.png"));
        fileLabel.setBounds((int) ((0.08 * 0.7 * height)), (int) (0.585 * 0.7 * height), 40, (int) (0.045 * 0.7 * height));
        fileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser("./"); // 文件选择器+默认路径
                fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt)", "txt")); // 选择类型
                int resultVal = fileChooser.showOpenDialog(null); // 显示选择器
                File chooseFile;
                if (resultVal == fileChooser.APPROVE_OPTION) { // 判断是否确定选择文件
                    chooseFile = fileChooser.getSelectedFile(); // 返回所选择的的文件
                    if (null != chooseFile) {
                        InputStream is = null;
                        boolean b = false;
                        String fileName = chooseFile.getName();
                        try {
                            is = new FileInputStream(chooseFile);
                            b = FtpUtils.uploadFile("39.107.249.220",
                                    21,
                                    "html_fs",
                                    "html_fs_pwd",
                                    "/home/html_fs",
                                    "/file",
                                    fileName,
                                    is);
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        if (b) {
                            DataUtils.client.send("@file^A^A^A" + fileName);
                        } else {
                            JOptionPane.showMessageDialog(null, "标题【错误】", "上传失败，请检查网络！", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        frame.add(fileLabel);

        //添加文件列表的图标
        JLabel flistLabel = new JLabel(new ImageIcon("img/surface/flist.png"));
        flistLabel.setBounds((int) ((0.16 * 0.7 * height)), (int) (0.585 * 0.7 * height), 40, (int) (0.045 * 0.7 * height));
        flistLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null == flistFrame || null == flistFrame.getFrame()) {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    flistFrame = new FlistFrame((int) p.getX(), (int) p.getY() - 315);
                }
            }
        });
        frame.add(flistLabel);

        /**
         * 广告位
         */
        //初始化三个图片
        ImageIcon[] ads = new ImageIcon[5];
        for (int i = 0; i < ads.length; i++) {
            ads[i] = new ImageIcon("img/ad/ad" + i + ".jpg");
            ads[i].setImage(ads[i].getImage().getScaledInstance((int) (0.22 * 0.6 * width) + 8,
                    (int) (0.585 * 0.7 * height), Image.SCALE_DEFAULT));
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
        memberList.setFont(new Font("System", Font.PLAIN, 18));
        memberList.setBounds(20, 1, (int) (0.2285 * 0.6 * width) - 20, (int) (0.32 * 0.7 * height));
        // 添加滚动条
        JScrollPane scrollPanel3 = new JScrollPane(memberList);
        scrollPanel3.setBounds(20, 1, (int) (0.2285 * 0.6 * width) - 20, (int) (0.32 * 0.7 * height));
        scrollPanel3.setBorder(null); // 去除边框
        panel4.add(scrollPanel3);
    }

    public static void main(String[] args) {
        new ChatFrame(null, null, null);
    }

}

class TuFrame {

    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public TuFrame(int x, int y) {
        frame = new JFrame();
        frame.setUndecorated(true);        //窗口去边框
        frame.setAlwaysOnTop(true);        //设置窗口总在最前
        frame.setBackground(new Color(0, 0, 0, 0));        //设置窗口背景为透明色
        frame.setBounds(x, y, 250, 115);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setBounds(0, 0, 250, 115);
        frame.add(panel);
        //添加关闭按钮
        ImageIcon closeIcon = new ImageIcon("img/surface/close.png");
        closeIcon.setImage(closeIcon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        JLabel closeBtn = new JLabel(closeIcon);
        closeBtn.setBounds(230, 0, 15, 15);
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                frame = null;
            }
        });
        panel.add(closeBtn);

        //添加表情包
        ImageIcon[] imgs = new ImageIcon[10];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = new ImageIcon("img/head/h" + i + ".jpg");
        }
        JLabel[] tuArr = new JLabel[10];
        for (int i = 0; i < tuArr.length; i++) {
            imgs[i].setImage(imgs[i].getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            tuArr[i] = new JLabel(imgs[i]);
            tuArr[i].setBounds(i % 5 * 50, (i / 5) * 50 + 15, 50, 50);
            // 监听点击事件，发送表情包
            int finalI = i;
            tuArr[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String inLine = "@img^A^A^Ah" + finalI + ".jpg";
                    DataUtils.client.send(inLine);
                    frame.dispose();
                    frame = null;
                }
            });
            panel.add(tuArr[i]);
        }
        frame.setVisible(true);
    }
}

class FlistFrame {
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public FlistFrame(int x, int y) {
        frame = new JFrame();
        frame.setUndecorated(true);        //窗口去边框
        frame.setAlwaysOnTop(true);        //设置窗口总在最前
        frame.setBackground(new Color(0, 0, 0, 0));        //设置窗口背景为透明色
        frame.setBounds(x, y, 200, 300);

        List<String> fileNameList = FtpUtils.getDirFiles(("39.107.249.220"),
                21,
                "html_fs",
                "html_fs_pwd",
                "/home/html_fs",
                "/file");

        JLabel[] bts = new JLabel[fileNameList.size()];
        JLabel[] jls = new JLabel[fileNameList.size()];
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(190, 30 * (jls.length + 1)));
        panel.setLayout(null);
        panel.setBackground(Color.white);

        //添加关闭按钮
        ImageIcon closeIcon = new ImageIcon("img/surface/close.png");
        closeIcon.setImage(closeIcon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        JLabel closeBtn = new JLabel(closeIcon);
        closeBtn.setBounds(150, 10, 15, 15);
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                frame = null;
            }
        });
        panel.add(closeBtn);

        for (int i = 0; i < jls.length; i++) {
            jls[i] = new JLabel(fileNameList.get(i));
            jls[i].setBounds(0, 30 + i * 30, 135, 30);
            bts[i] = new JLabel("下载");
            bts[i].setBounds(135, 30 + i * 30, 45, 30);
            bts[i].setForeground(Color.BLUE);
            int finalI = i;
            bts[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String filename = fileNameList.get(finalI);
                    JFileChooser fileChooser = new JFileChooser("./"); // 文件选择器+默认路径
                    fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件(*.txt)", "txt")); // 选择类型
                    int resultVal = fileChooser.showSaveDialog(null); // 显示选择器
                    if (resultVal == fileChooser.APPROVE_OPTION) { // 判断是否确定选择文件
                        File file = fileChooser.getSelectedFile();
                        if (null != file) {
                            if (!file.exists()) {
                                try {
                                    file.createNewFile();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                boolean b = FtpUtils.downFile("39.107.249.220",
                                        21,
                                        "html_fs",
                                        "html_fs_pwd",
                                        "file",
                                        filename,
                                        file.getParent(),
                                        file.getName());
                                if (b) {
                                    frame.dispose();
                                    frame = null;
                                    JOptionPane.showMessageDialog(null, "文件下载成功！", "【成功】", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "下载失败，请检查网络！", "【错误】", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                int response = JOptionPane.showConfirmDialog(null, "确认替换文件？", "确认", JOptionPane.YES_NO_OPTION);
                                if (response == 0) {
                                    boolean b = FtpUtils.downFile("39.107.249.220",
                                            21,
                                            "html_fs",
                                            "html_fs_pwd",
                                            "file",
                                            filename,
                                            file.getParent(),
                                            file.getName());
                                    if (b) {
                                        frame.dispose();
                                        frame = null;
                                        JOptionPane.showMessageDialog(null, "文件下载成功！", "【成功】", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "下载失败，请检查网络！", "【错误】", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("没有保存");
                    }
                }
            });
            panel.add(jls[i]);
            panel.add(bts[i]);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        //滚动条
        scrollPane.setBounds(0, 0, 200, 285);
        //设置横向滚动条不可见
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}
