package com.deekian.tools.io;

import java.io.*;

/**
 * 文件读写
 */
public class Fichier {
    public static byte[] readToStream(String filepath) throws IOException {
        File file = new File(filepath);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileContent);
        in.close();
        return fileContent;
    }

    public static String readToStr(String filepath) throws IOException {
        return readToStr(filepath, "UTF-8");
    }

    public static String readToStr(String filepath, String encode) throws IOException {
        if (encode == null) {
            encode = "UTF-8";
        }
        return new String(readToStream(filepath), encode);
    }

    public static boolean outToFile(String text, String target) {
        // 1：利用File类找到要操作的对象
        File file = new File(target);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        Writer out = null;
        try {
            //2：准备输出流
            out = new FileWriter(file);
            out.write(text);
            out.close();
            return true;
        } catch (IOException e) {
            System.out.printf("写入文件异常\n%s", e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
