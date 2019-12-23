package com.chat.server.utils;

import com.chat.server.thread.ServerThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-18 9:54
 */
public class ChatManager {

    private static OutputStream os;
    private static DataOutputStream dos;
    private static InputStream is;
    private static DataInputStream dis;

    /**
     * 客户端连接后，做初始化工作
     * @param client
     */
    public static void initConn(Socket client) {
        try {
            is = client.getInputStream();
            dis = new DataInputStream(is);
            String clientName = dis.readUTF();
            //加入客户端集合
            DataUtils.clientList.add(client);
            synchronized (DataUtils.online){
                String msg = "系统提示  " + DateUtils.getDate()+"\n    · "
                        + clientName + "已连接..." + "当前人数：" + ++DataUtils.online;
                ChatManager.sendToClients(msg);
            }
            //将client加入新线程
            new ServerThread(client, clientName).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把消息返回给每一个客户端
     * @param msg
     */
    public static void sendToClients(String msg) {
        for (int i = 0; i < DataUtils.clientList.size(); i++) {
            if (isClientClose(DataUtils.clientList.get(i))) {
                DataUtils.clientList.remove(i);
                i--;
                continue;
            }
            try {
                os = DataUtils.clientList.get(i).getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                throw new RuntimeException("服务端返回消息异常！");
            }
        }
    }

    /**
     * 判断客户端是否退出连接
     * @param socket
     * @return
     */
    private static boolean isClientClose(Socket socket) {
        try {
            //发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            socket.sendUrgentData(0xFF);
            return false;
        } catch (Exception se) {
            return true;
        }
    }
}
