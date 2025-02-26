package com.akd.common;


public class Constants {

    public static final String DOWNLOAD = "download";

    public static final String FAIL = "fail";

    public static final String SALT = "I0mZut94azwp0&VP";

    public static final String MD5 = "MD5";

    public static final String ENCODE_UTF8 = "utf-8";

    public static final int CAN_NOT_DOWNLOAD_KDM = 2;

    public static final int CAN_DOWNLOAD_KDM = 1;


    public static  String getRedisKeyQuery (String uuid, String certificate){
        return  new StringBuilder().append("REDIS_KEY_QUERY_").append(uuid).append("_").append(certificate).toString() ;

    }
    public  static String getRedisKeyIp(String ip){
        return  new StringBuilder().append("REDIS_KEY_IP_LIMIT_").append(ip).toString();
    }
    public  static String getRedisKey(String token){
        return  new StringBuilder().append("REDIS_KEY_TMS_TOKEN_").append(token).toString();
    }
    public  static String getRedisFlmKey(String token){
        return  new StringBuilder().append("REDIS_KEY_FLM_QUERY_").append(token).toString();
    }

}
