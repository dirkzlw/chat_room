package com.chat.server.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Ranger
 * @create 2019-12-18 16:10
 */
public class IOUtils {
    public static void closeIO(Closeable... io) {
        for (Closeable o : io) {
            if (null != o) {
                try {
                    o.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭IO流异常！");
                }
            }
        }
    }
}
