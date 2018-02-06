package com.example.a25467.moneymanager.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class FileUtils {

    /**
     * 创建文件
     */
    public static boolean createFile(File file){
        //如果文件存在，直接返回
        if (file.exists()){
            return true;
        }
        try {
            //如果要创建的是文件，先把文件夹创建出来，再创建文件
            file.getParentFile().mkdirs();
            file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 拷贝文件
     */
    public static void copyFile(InputStream in, File file){
        try {
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int readBytes = 0;
            while ((readBytes = in.read(buffer)) != -1)
                out.write(buffer, 0, readBytes);
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
