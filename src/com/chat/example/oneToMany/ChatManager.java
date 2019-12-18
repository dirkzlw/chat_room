package com.chat.example.oneToMany;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Ranger
 * @create 2019-12-18 9:54
 */
public class ChatManager {

    public static void sendToClients(String msg) {
        for (int i = 0; i < DataUtils.clientList.size(); i++) {

            if (isClientClose(DataUtils.clientList.get(i))) {
                synchronized (DataUtils.clientList) {
                    DataUtils.clientList.remove(i);
                    i--;
                }
                continue;
            }
            try {
                DataOutputStream dos = new DataOutputStream(DataUtils.clientList.get(i).getOutputStream());
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                throw new RuntimeException("服务端返回消息异常！");
            }
        }
    }

    private static boolean isClientClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }
}
