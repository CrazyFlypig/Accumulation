package data_encrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * 数据加密解密程序
 *
 * @author cc
 * @create 2018-03-14-16:50
 */

public class DataEncryptDecryptClass {
    private final static Logger logger = Logger.getLogger("EncryptImage");
    private final static String encrypt = "encrypt_Byte";
    private final static String decrypt = "decrypt_Byte";

    public static void main(String[] args) throws Exception {
        String password = "";
        String sourcePath = "";
        String targetDir = "";
        dirEnrypt(sourcePath, targetDir, password, encrypt);
    }

    /**
     *  递归处理源路径下的文件和目录
     * @param sourcePath 源文件/目录路径
     * @param targetDir 目标目录
     * @param password 密码
     * @param type 类型：加密/解密
     * @throws Exception IO异常
     */
    public static void dirEnrypt(String sourcePath, String targetDir, String password, String type) throws Exception {
        List<File> files = new ArrayList<>();
        List<File> dirs = new ArrayList<>();
        //路径处理
        sourcePath = pathFormat(sourcePath);
        targetDir = pathFormat(targetDir);
        targetDir = mkdirForPath(sourcePath, targetDir, type);
        //判断目标目录是否存在，若不存在，创建之
        File dir = new File(targetDir);
        if (!dir.exists()){
            dir.mkdir();
        }
        //获取sourcePath路径下的目录和文件
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()){
            System.out.println("选择路径有误！");
            return;
        }
        if (sourceFile.isDirectory()){
            File[] sourceFiles = sourceFile.listFiles();
            for (File f : sourceFiles){
                if (f.isDirectory())
                    dirs.add(f);
                if (f.isFile())
                    files.add(f);
            }
        }
        if (sourceFile.isFile()){
            files.add(sourceFile);
        }
        if (files.size() > 0){
            for (File f : files){
                imageEncrypt(f.getAbsolutePath(), targetDir, password);
            }
        }
        if (dirs.size() > 0){
            for (File f : dirs){
                dirEnrypt(f.getAbsolutePath(), targetDir, password, type);
            }
        }
    }

    /**
     *  获取源路径下的所有文件，并对其进行加密解密操作
     * @param sourcePath 源路径
     * @param targetDir 目标路径
     * @param password 密码
     * @throws Exception IO 异常
     */
    public static void imageEncrypt(String sourcePath, String targetDir, String password) throws Exception {
        //路径处理
        sourcePath = pathFormat(sourcePath);
        targetDir = pathFormat(targetDir);
        //获取sourcePath下的所有文件
        File sourceFile = new File(sourcePath);
        List<File> files ;
        if (!sourceFile.exists()){
            System.out.println("选择路径有误！");
            return;
        }
        if (sourceFile.isDirectory()){
            //如果路径是目录
            files = Arrays.asList(sourceFile.listFiles());
        }else {
            files = new ArrayList<>();
            files.add(sourceFile);
        }
        //判断密码是否为空
        if (password == null || password.equals("")){
            System.out.println("密码有误。");
            return;
        }
        //加密所有文件
        int count = 0;
        for (File file : files){
            if (file.exists() && file.isFile()){
                File targetFile = new File(targetDir + "/" + file.getName());
                targetFile.createNewFile();
                encryptOrdecrypt(file.getAbsolutePath(), targetFile.getAbsolutePath(), password);
                System.out.println(++count + "\t" + file.getName() + "encrypt successfully!");
            }
        }
    }

    /**
     *  路径格式化
     * @param path 目录/文件路径
     * @return 格式化后的路径
     */
    public static String pathFormat(String path){
        //路径处理，"\\"转"/"
        String[] paths = path.split("\\\\");
        StringBuffer buffer = new StringBuffer();
        for (String p : paths){
            buffer.append(p + "/");
        }
        return buffer.toString().substring(0, buffer.length()-1);
    }

    /**
     *  解密/解密 文件
     * @param path 源文件路径
     * @param targetDir 目标文件路径
     * @param password 密码
     * @throws Exception IO异常
     */
    public static void encryptOrdecrypt(String path, String targetDir, String password) throws Exception {
        //int factor = password.hashCode();
        byte[] factor = password.getBytes();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetDir));
        byte[] buffer = new byte[factor.length];
        while (bis.read(buffer) != -1){
            for (int i = 0 ; i < buffer.length ; i++){
                buffer[i] ^= factor[i];
            }
            bos.write(buffer);
        }
        bis.close();
        bos.close();
    }

    /**
     *  构造与源目录同等目录等级的目标目录
     * @param path 源文件/目录 路径
     * @param targetDir 目标目录
     * @param type 类型：加密/解密
     * @return 目标目录
     */
    public static String mkdirForPath(String path ,String targetDir, String type){
        File file = new File(path);
        StringBuffer buffer = new StringBuffer();
        if (!file.exists()){
            System.out.println("输入路径有误！");
            return null;
        }
        if (file.isFile()){
            String[] paths = path.split("/");
            for (int i = 0 ; i < paths.length-1 ; i++){
                buffer.append(paths[i] + "/");
            }
        }else {
            buffer.append(path);
        }
        String[] buffers = buffer.toString().split("/");
        if (!targetDir.endsWith("/")){
            targetDir += "/";
        }
        targetDir += buffers[buffers.length-1] + "_" + type;
        return targetDir;
    }
}
