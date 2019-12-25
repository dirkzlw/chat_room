package com.chat.client.test;

import com.chat.client.utils.FtpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Ranger
 * @create 2019-12-19 20:39
 */
public class FtpTest {
    public static void main(String[] args){
//        boolean b = FtpUtils.downFile("39.107.249.220",
//                21,
//                "html_fs",
//                "html_fs_pwd",
//                "img",
//                "scul.png",
//                "d:/",
//                "dirk.png");
//        System.out.println("b = " + b);

        try {
            InputStream is =new FileInputStream(new File("d:/zf/m.txt"));
            boolean b = FtpUtils.uploadFile("39.107.249.220",
                    21,
                    "html_fs",
                    "html_fs_pwd",
                    "/home/html_fs",
                    "/img",
                    "logo.png",
                    is);
            System.out.println("b = " + b);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
