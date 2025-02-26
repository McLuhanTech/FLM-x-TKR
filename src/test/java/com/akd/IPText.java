package com.akd;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Copyright neil <neilren@outlook.com>
 * @Author:
 * @Date: 2019/9/22
 * @Description:
 */
public class IPText {

    public static void main(String[] ages){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip=inetAddress.getHostAddress();//获得本机Ip
        System.out.println(ip);
    }
}
