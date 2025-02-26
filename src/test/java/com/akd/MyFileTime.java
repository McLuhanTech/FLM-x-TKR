package com.akd;

import com.akd.common.result.ResultBody;
import com.akd.common.result.err.ErrorInfoEnum;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class MyFileTime {


    public static void main(String[] args) {
       Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.out.println(date);
        System.out.println(format);
        int i = 123;
        String s = String.valueOf(i);
        System.out.println(s);
        Date date1 =new Date();
        System.out.println(date1.getTime());
    }
}