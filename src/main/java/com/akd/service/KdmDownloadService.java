package com.akd.service;

import com.akd.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KdmDownloadService {

  public void  kdmDownload(HttpServletRequest request, HttpServletResponse response, String uuid, String certificate) throws UnsupportedEncodingException {
      Map map = new HashMap();
      map.put("uuid",uuid);
      map.put("certificate",certificate);
      String str = HttpUtils.doPost("http://39.106.123.164:8040/api/v1/kdm/query", null, map);
      String result = JSONObject.parseObject(str).getString("result");
      JSONObject jsonObject1 = JSONObject.parseObject(result);
      String title = jsonObject1.getString("title");
      String content = jsonObject1.getString("content");
      String userAgent = request.getHeader("User-Agent");
      // 针对IE或者以IE为内核的浏览器：
      if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
          title = java.net.URLEncoder.encode(title, "UTF-8");
      } else {
          // 非IE浏览器的处理：
          title = new String(title.getBytes("UTF-8"), "ISO-8859-1");
      }
      response.setContentType("multipart/form-data");
      response.setCharacterEncoding("UTF-8");
      response.setHeader("Content-Disposition",  String.format("attachment; filename=\"%s\"", title));
      OutputStream outputStream = null;
      try {
          outputStream = response.getOutputStream();
          outputStream.write(content.getBytes());
      } catch (IOException e) {
          log.error("downloadKdm-下载失败",e);
      }finally {
          if (outputStream != null) {
              try {
                  outputStream.close();
              } catch (Exception e2) {
                  log.info("关闭输入流时出现错误", e2);
              }
          }

      }
  }

}
