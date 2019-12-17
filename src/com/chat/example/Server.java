package com.chat.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-17 18:16
 */
public class Server {
    public static void main(String[] args){
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(80);
                Socket client = server.accept();
                DataInputStream dis  = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true){
                    //接收客户端信息
                    String getStr = dis.readUTF();
                    System.out.println("客户端：" + getStr);

                    //获取控制台输入，发送至客户端
                    String toStr = br.readLine();
                    dos.writeUTF(toStr);
                    dos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
