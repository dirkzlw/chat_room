package com.chat.server.thread;

import com.chat.server.utils.ChatManager;
import com.chat.server.utils.DataUtils;
import com.chat.server.utils.DateUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * 监听客户端发送的消息，并返回
 *
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
                    synchronized (this) {
                        String line = dis.readUTF();
                        if ("@exit^A^A^A".equals(line)) {
                            synchronized (DataUtils.online) {
                                String msg = "系统提示  " + DateUtils.getDate() + "\n    · "
                                        + clientName + "已退出群聊..." + "当前人数：" + --DataUtils.online;
                                client.close();
                                ChatManager.sendToClients(msg);
                            }
                            continue;
                        } else if (line.contains("@img^A^A^A")) {
                            //处理的表情包
                            synchronized (DataUtils.online) {
                                String msg = clientName + "  " + DateUtils.getDate() + "\n    · "
                                        + line;
                                ChatManager.sendToClients(msg);
                            }
                            continue;
                        }else if(line.contains("@file^A^A^A")){
                            //处理文件
                            synchronized (DataUtils.online) {
                                String msg = clientName + "  " + DateUtils.getDate() + "\n    · "
                                        + line;
                                ChatManager.sendToClients(msg);
                            }
                            continue;
                        }
                        String msg = clientName + "  " + DateUtils.getDate() + "\n  · "
                                + line;
                        ChatManager.sendToClients(msg);
                    }
                } catch (SocketException e) {
                    synchronized (DataUtils.online) {
                        String msg = "系统提示  " + DateUtils.getDate() + "\n    · "
                                + clientName + "已退出群聊..." + "当前人数：" + --DataUtils.online;
                        ChatManager.sendToClients(msg);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
