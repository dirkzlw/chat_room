package com.chat.example.oneToMany;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-18 10:07
 */
public class ClientThread extends Thread {

    private Socket server;

    public ClientThread(Socket socket){
        this.server = socket;
    }

    @Override
    public void run() {
        try {
            String line;
            while (true){
                DataInputStream dis = new DataInputStream(server.getInputStream());
                line = dis.readUTF();
                System.out.println(line);
            }
        }catch (Exception e){
            new RuntimeException("客户端接收服务端消息异常！");
        }
    }
}
