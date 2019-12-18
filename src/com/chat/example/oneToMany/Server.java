package com.chat.example.oneToMany;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-17 20:45
 */
public class Server {

    public static void main(String[] args) {
        int port = 8088;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("server建立,等待客户端连接...");
            while (true) {
                Socket client = server.accept();
                String clientName = new DataInputStream(client.getInputStream()).readUTF();
                DataUtils.clientList.add(client);
//                System.out.println(clientName + "已连接..." + "当前人数：" + ++DataUtils.online);
                ChatManager.sendToClients(clientName + "已连接..." + "当前人数：" + ++DataUtils.online);
                new ServerThread(client, clientName).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
