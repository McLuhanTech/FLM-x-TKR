package com.akd;


import com.akd.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DownloadKdmTest {

    public static  void main(String[] ages){
        Map map = new HashMap();
        map.put("uuid","urn:uuid:edc6470d-6da0-4839-ba86-2f0000002677");
        map.put("certificate","VZf7nSTdxMscCwhxK+0000002677");
        String str = HttpUtils.doPost("http://39.106.123.164:8040/api/v1/kdm/query", null, map);
        String result = JSONObject.parseObject(str).getString("result");
        JSONObject jsonObject1 = JSONObject.parseObject(result);
        String title = jsonObject1.getString("title");
        String content = jsonObject1.getString("content");
        System.out.println(str);
        File file = new File("H:\\"+title);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte bytes[]= new byte[1024*8];
        bytes=content.getBytes();
        int b=content.length();
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes,0,b);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
