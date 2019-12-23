package com.chat.client.test;

/**
 * @author Ranger
 * @create 2019-12-23 20:34
 */
public class SeTest {
    public static void main(String[] args){
        String s = ",11,22,33,4,,";
        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println("s1 = " + s1);
        }
    }
}
