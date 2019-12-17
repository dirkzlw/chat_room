package com.chat.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-17 18:17
 */
public class Client {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket client = new Socket("127.0.0.1", 80);
                DataInputStream dis  = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true){
                    //获取控制台输入，发送至服务端
                    String toStr = br.readLine();
                    dos.writeUTF(toStr);
                    dos.flush();

                    //接收服务端信息
                    String getStr = dis.readUTF();
                    System.out.println("服务端：" + getStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
