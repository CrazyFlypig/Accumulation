import java.io.*;

public class IOUtils {
    /**
     * 输入输出流拷贝
     * @param in    输入流
     * @param out   输出流
     * @throws IOException  IO异常
     */
    public static void copy(InputStream in, FileOutputStream out) throws IOException {
        byte[] buffer = new byte[1024 * 1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1){
            out.write(buffer, 0, length);
            out.flush();
        }
    }

    /**
     * 如果目录不存在，则创建目录
     * @param dirPath 目录路径
     */
    public static void mkdir(String dirPath){
        File file = new File(dirPath);
        if (!file.exists()){
            file.mkdir();
        }
    }

    /**
     * 创建文件，如果文件存在，则覆盖
     * @param filePath 文件路径
     */
    public static void createNewFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
    }
}
