import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FTPActionClass {

    private String url;
    private Integer port;
    private String username;
    private String password;
    //ftp连接重试次数，默认是3
    private Integer connTimes = 3;
    private FTPClient ftpClient;

    public FTPActionClass(String url, int port, String username, String password){
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        ftpClient = new FTPClient();
    }
    public FTPActionClass(String url, int port, String username, String password, int connTimes){
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connTimes = connTimes;
        ftpClient = new FTPClient();
    }

    /**
     * 建立ftp连接
     * @throws Exception
     */
    public void connFTP() throws Exception{
        try {
            //如果之前有连接，则断开之前的连接
            if (ftpClient.isConnected()){
                disconnFTP();
            }
            for (int times = 0 ; times < connTimes ; times++){
                if (port == null){
                    //默认端口
                    ftpClient.connect(url);
                }else {
                    //其他端口
                    ftpClient.connect(url,port);
                }
                //登录
                ftpClient.login(username,password);
                if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                    System.out.println("成功连接至FTP：" + url);
                    break;
                }else {
                    System.out.println("连接FTP失败：" + url + "，剩余重试次数：" + (connTimes-times-1));
                }
            }
        } catch (SocketException e1) {
            throw new Exception("连接FTP异常：" + e1.getMessage());
        } catch (IOException e2) {
            throw new Exception("连接FTP异常：" + e2.getMessage());
        }
    }

    /**
     * 断开ftp连接
     * @throws Exception
     */
    public void disconnFTP() throws Exception {
        if (ftpClient.isConnected()){
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("断开ftp连接：" + url);
            } catch (IOException e) {
                throw new Exception("断开FTP连接异常：" + e.getMessage());
            }
        }
    }

    /**
     * 文件上传
     * @param path 文件路径
     * @param fileName 文件名
     * @param localFilePath 本地文件路径
     * @return 是否成功上传
     */
    public boolean uploadFile(String path, String fileName, String localFilePath){
        return true;
    }

    /**
     * 文件下载
     * @param remotePath ftp目录
     * @param fileNameList  文件名列表
     * @param localPath 本地目录
     * @return  是否成功
     */
    public boolean downFile(String remotePath, List<String> fileNameList, String localPath) throws Exception {
        boolean success = false;
        Set<String> fileNameSet = new HashSet<String>();
        for (String fileName : fileNameList){
            fileNameSet.add(fileName);
        }
        if (!ftpClient.isConnected()){
            connFTP();
        }
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            IOUtils.mkdir(localPath);
            //转移到FTP服务器的目录
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles){
                String fileName = file.getName();
                if (fileNameSet.contains(fileName)){
                    File localFile = new File(localPath + "/" + fileName);
                    IOUtils.createNewFile(localFile.getAbsolutePath());
                    fos = new FileOutputStream(localFile);
                    is = ftpClient.retrieveFileStream(fileName);
                    IOUtils.copy(is,fos);
                }
            }
            success = true;
        } catch (FileNotFoundException e) {
            throw new Exception("文件下载异常：" + e.getMessage());
        } catch (IOException e) {
            throw new Exception("文件下载异常：" + e.getMessage());
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * 递归目录下载
     * @param remotePath ftp目录
     * @param localPath 本地目录
     */
    public void downDir(String remotePath, String localPath) throws Exception {
        if (remotePath == null || remotePath.equals("") || localPath == null || localPath.equals("")){
            throw new Exception("目录下载接收参数有误，remotePath：" + remotePath + "，localPath：" + localPath);
        }
        if (!ftpClient.isConnected()){
            connFTP();
        }
        FTPFile[] ftpFiles = null;
        try {
            //切换工作目录
            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(),"ISO-8859-1"));
            ftpFiles = ftpClient.listFiles();
            List<String> files = new ArrayList<String>();
            for (FTPFile file : ftpFiles){
                if (!file.isDirectory()){
                    files.add(file.getName());
                    //downFile(remotePath,new ArrayList<String>().add(file.getName()),localPath);
                }else {
                    downDir(file.getName(),localPath);
                }
            }
            downFile(remotePath,files,localPath);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("字符串编码异常：" + e.getMessage());
        } catch (IOException e) {
            throw new Exception("递归目录异常：" + e.getMessage());
        } catch (Exception e) {
            throw new Exception("递归目录异常：" + e.getMessage());
        }
    }
}
