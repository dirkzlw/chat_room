package com.chat.server.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Ranger
 * @create 2019-12-23 20:08
 */
public class SeTest {
    public static void main(String[] args){
        Map<String,Double> map = new HashMap<>();
        map.put("a", 1d);
        map.put("b", 2d);
        map.put("c", 3d);
        map.put("d", 4d);
        Set<String> strings = map.keySet();
        for (String string : strings) {
            System.out.println("string = " + string);
        }
    }
}
