package com.chat.server.socket;

import com.chat.server.thread.ServerThread;
import com.chat.server.utils.ChatManager;
import com.chat.server.utils.DataUtils;

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
            System.out.println("服务端启动,等待客户端连接...");
            while (true) {
                Socket client = server.accept();
                ChatManager.initConn(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
