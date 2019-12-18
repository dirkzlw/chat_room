package com.chat.example.oneToMany;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-17 21:20
 */
public class DataUtils {
    public static Integer online = 0;
    public static List<Socket> clientList = new ArrayList<>();
}
