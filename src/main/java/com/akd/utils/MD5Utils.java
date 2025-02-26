package com.akd.utils;

import com.akd.common.Constants;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author mengfanzhu
 * @Date 8/21/17 17:03
 * @Version 1.0
 */
public class MD5Utils {

    public static String encoderByMd5(String str) {
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance(Constants.MD5);
            //加密后的字符串
            return new BASE64Encoder().encode(md5.digest(str.getBytes(Constants.ENCODE_UTF8)));
        } catch (Exception e) {
            return UUIDGenerator.getUUID() + System.currentTimeMillis();
        }
    }

    /**
     * 生成字符串的md5码
     *
     * @param str 字符串
     * @return md5码
     */
    public static String md5(String str) {
        MessageDigest alga = null;
        try {
            alga = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        alga.update(str.getBytes());
        return byteTohex(alga.digest());
    }

    /**
     * 将二进制比特数组转化为字符串
     *
     * @param b
     * @return
     */
    private static String byteTohex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (byte element : b) {
            stmp = (Integer.toHexString(element & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String md5File(String fileUrl) {
        try {
            return DigestUtils.md5Hex(new FileInputStream(fileUrl));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptPassword(String str1,String str2){
        return md5(str1+str2);
    }


    public static void main(String[] args) {
        String str = MD5Utils.encryptPassword("000000000001","111111");
        System.err.println(str);
    }
}
