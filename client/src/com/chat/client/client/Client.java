package com.chat.client.client;

import com.chat.client.po.User;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-23 10:14
 */
public class Client {
    DataOutputStream dos;
    @Setter
    private JTextPane textPane;
    @Setter
    private JLabel jlist;
    @Setter
    private JTextArea memberList;
    @Setter
    private List<User> userList;
    @Getter
    private Socket server;
    //初始化连接
    public Client() {
        try {
            server = new Socket("127.0.0.1", 8088);
            dos = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "服务器已关闭...3秒后退出程序!", "【错误】", JOptionPane.ERROR_MESSAGE);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        }
    }

    //发送消息
    public void send(String inLine) {
        try {
            dos.writeUTF(inLine);
            dos.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "服务器已关闭...3秒后退出程序!", "【错误】", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
