package com.akd.utils;

public class StringUtil {

    public static String zeroFill(int num, int count){
        String res = "";
        int len = String.valueOf(num).length();
        int needLen = count - len;
        for(int i=0; i<needLen; i++){
            res = res + "0";
        }
        return res + num;
    }

}
