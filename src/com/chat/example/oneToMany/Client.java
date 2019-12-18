package com.chat.example.oneToMany;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Ranger
 * @create 2019-12-17 20:58
 */
public class Client {
    public static void main(String[] args){

        try {
            Socket server = new Socket("127.0.0.1", 8088);
            new ClientThread(server).start();
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());
            //随机生成名字发送至server
            dos.writeUTF(String.format("%04d", (int)(Math.random() * 10000)));
            dos.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (!"exit".equals(line = br.readLine())){
                try {
                    dos.writeUTF(line);
                    dos.flush();
                } catch (SocketException e){
                    System.out.println("服务器已关闭...");
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            System.out.println("服务器已关闭...");
            System.exit(0);
        }
    }
}
