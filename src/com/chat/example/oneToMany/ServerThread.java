package com.chat.example.oneToMany;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Ranger
 * @create 2019-12-17 20:48
 */
public class ServerThread extends Thread {

    private Socket client;
    private String clientName;

    public ServerThread() {
    }

    public ServerThread(Socket socket, String name) {
        this.client = socket;
        this.clientName = name;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            while (true) {
                try {
                    String line = dis.readUTF();
                    System.out.println(clientName + ":" + line);
                } catch (SocketException e) {
                    System.out.println(clientName + "已退出群聊..." + "当前人数：" + --PublicUtils.online);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
