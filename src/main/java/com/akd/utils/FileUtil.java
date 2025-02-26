package com.akd.utils;


import java.io.*;
import java.security.MessageDigest;

public class FileUtil {

    public static String readToString(String filePathAndName) {
        String encoding = "UTF-8";
        File file = new File(filePathAndName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }


    public static String getFileMd5(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return "";
        }
        byte[] buffer = new byte[2048];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while(true){
                int len = in.read(buffer,0,2048);
                if(len != -1){
                    digest.update(buffer, 0, len);
                }else{
                    break;
                }
            }
            in.close();

            byte[] md5Bytes  = digest.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeToFile(String content, String path){
        FileWriter fileWriter = null;
        File file = new File(path);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(content, 0, content.length());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
