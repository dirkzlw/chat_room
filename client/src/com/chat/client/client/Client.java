package com.chat.client.client;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-23 10:14
 */
public class Client {
    DataOutputStream dos;

    //初始化连接
    public Client(String username, JTextArea textShow) {
        try {
            Socket server = new Socket("127.0.0.1", 8088);
            new ClientThread(server,textShow).start();
            dos = new DataOutputStream(server.getOutputStream());
            //随机生成名字发送至server
            dos.writeUTF(username);
            dos.flush();

        } catch (IOException e) {
            System.out.println("服务器已关闭...3秒后退出程序!");
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
        } catch (Exception e){
            System.out.println("服务器已关闭...");
            System.exit(0);
        }
    }
}
