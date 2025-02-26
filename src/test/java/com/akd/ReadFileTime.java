package com.akd;

import com.akd.common.OSinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 读取文件创建时间和最后修改时间
 */
public class ReadFileTime {

    public static final String filePath = "H:\\akd_test\\work\\20190921\\download\\17\\1.xml";

    public static void main(String[] args) {
        getCreateTime(filePath);
        getModifiedTime_1();
        getModifiedTime_2();
    }

    /**
     * 读取文件创建时间
     */
    public static String getCreateTime(String filePath){
        String strTime = null;

        if(OSinfo.isWindows()){
            try {
                Process p = Runtime.getRuntime().exec("cmd /C dir "
                        + filePath + "/tc" );
                InputStream is = p.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while((line = br.readLine()) != null){
                    if(line.endsWith(".xml")){
                        strTime = line.substring(0,17);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {




        }

        System.out.println("创建时间    " + strTime);
        return strTime;
    }
    /**
     * 读取文件修改时间的方法1
     */
    @SuppressWarnings("deprecation")
    public static void getModifiedTime_1(){
        File f = new File(filePath);
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        cal.setTimeInMillis(time);
        //此处toLocalString()方法是不推荐的，但是仍可输出
        System.out.println("修改时间[1] " + cal.getTime().toLocaleString());
        //输出：修改时间[1]    2009-8-17 10:32:38
    }

    /**
     * 读取修改时间的方法2
     */
    public static void getModifiedTime_2(){
        File f = new File(filePath);
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
        //输出：修改时间[2]    2009-08-17 10:32:38
    }
}