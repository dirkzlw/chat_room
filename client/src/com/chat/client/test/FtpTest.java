package com.chat.client.test;

import com.chat.client.utils.FtpUtils;

/**
 * @author Ranger
 * @create 2019-12-19 20:39
 */
public class FtpTest {
    public static void main(String[] args){
        boolean b = FtpUtils.downFile("39.107.249.220",
                21,
                "html_fs",
                "html_fs_pwd",
                "img",
                "scul.png",
                "d:/",
                "dirk.png");
        System.out.println("b = " + b);
    }
}
