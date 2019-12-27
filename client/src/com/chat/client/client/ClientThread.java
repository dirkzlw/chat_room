package com.chat.client.client;

import com.chat.client.po.User;
import com.chat.client.utils.DataUtils;
import com.chat.client.view.OneChatFrame;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-18 10:07
 */
public class ClientThread extends Thread {

    private Socket server;
    private JTextPane textPane;
    private JLabel jlist;
    private JTextArea memberList;
    private List<User> userList;

    public ClientThread(Socket socket, JTextPane textPane, JLabel jlist, JTextArea memberList, List<User> userList) {
        this.server = socket;
        this.textPane = textPane;
        this.jlist = jlist;
        this.memberList = memberList;
        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            String line;
            while (true) {
                DataInputStream dis = new DataInputStream(server.getInputStream());
                line = dis.readUTF();
                System.out.println(line);
                if (line.contains("@list^A^A^A")) {
                    //群聊
                    String[] split = line.split("@list\\^A\\^A\\^A");
                    //发送的是图片
                    if (split[0].contains("@img^A^A^A")) {
                        String[] split2 = split[0].split("@img\\^A\\^A\\^A");
                        //先展示文本
                        Document doc = textPane.getDocument();
                        doc.insertString(doc.getLength(), split2[0], null);
                        //展示图片
                        String img = split2[1];
                        ImageIcon imageIcon = new ImageIcon("img/head/" + img);
                        int w = imageIcon.getIconWidth();
                        int h = imageIcon.getIconHeight();
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100 * h / w, Image.SCALE_DEFAULT));
                        textPane.insertIcon(imageIcon);
                        doc.insertString(doc.getLength(), "\n", null);
                    } else if (split[0].contains("@file^A^A^A")) {
                        //发送的是文件
                        String[] split2 = split[0].split("@file\\^A\\^A\\^A");
                        //先展示文本
                        Document doc = textPane.getDocument();
                        doc.insertString(doc.getLength(), split2[0] + "[文件] " + split2[1] + "\n", null);
                    } else {
                        Document doc = textPane.getDocument();
                        doc.insertString(doc.getLength(), split[0] + "\n", null);
                    }
                    //展示在线群成员列表
                    String[] split1 = split[1].split("\\,");
                    int count = 1;
                    memberList.setText("");
                    //遍历获取在线用户
                    for (String s : split1) {
                        memberList.append("  [在线]  " + s + "\n");
                    }
                    //遍历获取离线用户
                    for (User u : userList) {
                        boolean flag = false;
                        for (String s : split1) {
                            if (u.getUsername().equals(s)) {
                                flag = true;
                                count++;
                                break;
                            }
                        }
                        if (!flag)
                            memberList.append("  [离线]  " + u.getUsername() + "\n");
                    }
                    jlist.setText("群成员(" + count + "/" + (userList.size() + 1) + ")");
                } else if (line.contains("@user^A^A^A")) {
                    //一对一私聊
                    String[] split = line.split("@user\\^A\\^A\\^A");
                    String username = split[0];
                    String msg = split[1];
                    OneChatFrame oneChatFrame = DataUtils.client.getOneChatFrameMap().get(username);
                    Document doc = oneChatFrame.getOneChatPane().getDocument();
                    doc.insertString(doc.getLength(), username + "\n    " + msg + "\n", null);

                }
            }
        } catch (Exception e) {
            new RuntimeException("客户端接收服务端消息异常！");
        }
    }
}
