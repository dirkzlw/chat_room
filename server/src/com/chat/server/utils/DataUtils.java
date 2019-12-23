package com.chat.server.utils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ranger
 * @create 2019-12-17 21:20
 */
public class DataUtils {
    public static Integer online = 0;
    public static Map<String, Socket> clientMap = new HashMap<>();
}
