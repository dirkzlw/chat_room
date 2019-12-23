package com.chat.client.client;

import javax.swing.*;
import java.io.DataInputStream;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-18 10:07
 */
public class ClientThread extends Thread {

    private Socket server;
    private JTextArea textShow;

    public ClientThread(Socket socket,JTextArea textArea){
        this.server = socket;
        this.textShow = textArea;
    }

    @Override
    public void run() {
        try {
            String line;
            while (true){
                DataInputStream dis = new DataInputStream(server.getInputStream());
                line = dis.readUTF();
                System.out.println(line);
                textShow.append(line+"\n");
                System.out.println(textShow.getText());
            }
        }catch (Exception e){
            new RuntimeException("客户端接收服务端消息异常！");
        }
    }
}
