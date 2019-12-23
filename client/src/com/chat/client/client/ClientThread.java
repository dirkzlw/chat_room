package com.chat.client.client;

import com.chat.client.po.User;

import javax.swing.*;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-18 10:07
 */
public class ClientThread extends Thread {

    private Socket server;
    private JTextArea textShow;
    private JLabel jlist;
    private JTextArea memberList;
    private List<User> userList;

    public ClientThread(Socket socket, JTextArea textArea, JLabel jlist, JTextArea memberList, List<User> userList) {
        this.server = socket;
        this.textShow = textArea;
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
                System.out.println("dis = " + line);
                String[] split = line.split("@list\\^A\\^A\\^A");
                textShow.append(split[0] + "\n");
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
            }
        } catch (Exception e) {
            new RuntimeException("客户端接收服务端消息异常！");
        }
    }
}
