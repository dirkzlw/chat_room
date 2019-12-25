package com.chat.client.utils;

import com.chat.client.po.User;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-12-15 11:52
 */
public class FtpUtils {

    public static List<String> getDirFiles(String host,
                                           int port,
                                           String username,
                                           String password,
                                           String basePath,
                                           String filePath){
        List<String> fileList = new ArrayList<>();
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
            }
            //切换到上传目录
            if (ftp.changeWorkingDirectory(basePath + filePath)) {
                FTPFile[] ftpFiles = ftp.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    fileList.add(new String(ftpFile.getName().getBytes("ISO-8859-1"),"UTF-8"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return fileList;
    }
    /**
     * 读取指定用户头像
     * 如不存在则根据指定url下载
     * @param user
     * @return
     */
    public static String readHeadImg(User user){
        String[] split = user.getHeadUrl().split("/");
        File f = new File("img/head/" + split[split.length-1]);
        if (!f.exists()) {
            boolean b = FtpUtils.downFile("39.107.249.220",
                    21,
                    "html_fs",
                    "html_fs_pwd",
                    "img",
                    split[split.length - 1],
                    "img/head",
                    split[split.length-1] );
            if(!b){
                System.out.println("下载头像失败！");
            }
        }
        return split[split.length-1];
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url        FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param srcFileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @param toFileName 目标文件名
     * @return
     * @Version1.0
     */
    public static boolean downFile(String url,
                                   int port,
                                   String username,
                                   String password,
                                   String remotePath,
                                   String srcFileName,
                                   String localPath,
                                   String toFileName) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (new String(ff.getName().getBytes("ISO-8859-1"),"UTF-8").equals(srcFileName)) {
                    File localFile = new File(localPath + "/" + toFileName);
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host,
                                     int port,
                                     String username,
                                     String password,
                                     String basePath,
                                     String filePath,
                                     String filename,
                                     InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                            //单纯是创建目录
                            if (null == filename) {
                                return true;
                            }
                        }
                    }
                }
            }
            //为了加大上传文件速度，将InputStream转成BufferInputStream
            BufferedInputStream in = new BufferedInputStream(input);
            //加大缓存区
            ftp.setBufferSize(1024 * 1024);
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(new String(filename.getBytes("UTF-8"), "iso-8859-1"), in)) {
                return result;
            }
            in.close();
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 删除上传文件
     *
     * @param host      主机
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @param basePath  基础路径
     * @param filePaths 文件路径
     * @return
     */
    public static boolean delFile(String host,
                                  int port,
                                  String username,
                                  String password,
                                  String basePath,
                                  String[] filePaths) {
        boolean result = false;

        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            System.out.println("reply = " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            for (String filePath : filePaths) {
                //切换到删除目录
                if (filePath.contains(".")) {
                    ftp.deleteFile(basePath + "/" + filePath);
                } else {
                    FTPFile[] ftpFiles = ftp.listFiles(basePath + "/" + filePath);
                    for (FTPFile ftpFile : ftpFiles) {
                        if (ftpFile.isDirectory()) {
                            delDir(ftp, basePath + "/" + filePath + "/" + ftpFile.getName());
                        } else if (ftpFile.isFile()) {
                            ftp.deleteFile(basePath + "/" + filePath + "/" + ftpFile.getName());
                        }
                        ftp.removeDirectory(basePath + "/" + filePath + "/" + ftpFile.getName());
                    }
                    ftp.removeDirectory(basePath + "/" + filePath);
                }
            }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

        return result;
    }

    /**
     * 递归删除目录及文件
     *
     * @param ftp
     * @param dir
     * @throws IOException
     */
    private static void delDir(FTPClient ftp, String dir) throws IOException {
        FTPFile[] ftpFiles = ftp.listFiles(dir);
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isDirectory()) {
                delDir(ftp, dir + "/" + ftpFile.getName());
            } else if (ftpFile.isFile()) {
                ftp.deleteFile(dir + "/" + ftpFile.getName());
            }
            ftp.removeDirectory(dir + "/" + ftpFile.getName());
        }
    }
}
