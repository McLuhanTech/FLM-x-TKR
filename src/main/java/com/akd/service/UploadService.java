package com.akd.service;


import com.akd.common.Constants;
import com.akd.entity.KdmFailInfo;
import com.akd.entity.KdmInfo;
import com.akd.entity.response.KdmQuerRsponse;
import com.akd.utils.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class UploadService {

    @Value("${BASE_DIR_WORK}")
    private String baseDirWork;

    @Value("${KDM_RESOLVE_LIMIT}")
    private int kdmResolveLimit;

    @Autowired
    private KDMInfoService kdmInfoService;

    @Autowired
    private KDMFailInfoService kdmFailInfoService;

    @Autowired
    protected SimpleRedisService simpleRedisService;


    public void traverseFolder(String path) {
        File file = new File(path);
        String contentTitleText = null;
        String compositionPlaylistId = null;
        String certificateThumbprint = null;
        String[] lastFileMd5AndDate = null;
        long splitTime = 0;
        long fileSplitTime = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (file.exists()) {
            File[] uploadFiles = file.listFiles();
            if (null == uploadFiles || uploadFiles.length == 0) {
                log.info(path + "文件夹是空");
                return;
            } else {
                for (File uploadFile : uploadFiles) {
                    if (uploadFile.isDirectory()) {
                        log.info("文件夹:" + uploadFile.getAbsolutePath());
                        traverseFolder(uploadFile.getAbsolutePath());
                    } else {
                        String fileName = uploadFile.getName();
                        String[] currentFileSplit = getFileMd5AndNowDateStr(uploadFile.getAbsolutePath()).split("_");
                        String fileMd5AndDateStr = simpleRedisService.getString(fileName);
                        if(fileMd5AndDateStr != null){
                            lastFileMd5AndDate = fileMd5AndDateStr.split("_");
                            log.info("已扫描到的文件:{}", uploadFile.getAbsolutePath());
                        }else{
                            simpleRedisService.setString(fileName, getFileMd5AndNowDateStr(uploadFile.getAbsolutePath()));
                            log.info("新文件:{}", uploadFile.getAbsolutePath());
                            continue;
                        }
                        try {
                            splitTime = simpleDateFormat.parse(lastFileMd5AndDate[1]).getTime();
                            fileSplitTime = simpleDateFormat.parse(currentFileSplit[1]).getTime();
                        } catch (ParseException e) {
                            log.error("simpleDateFormat.parse error:", e);
                        }
                        if( currentFileSplit[0].equals(lastFileMd5AndDate[0]) && (fileSplitTime - splitTime) > kdmResolveLimit){
                              String workFilePath = baseDirWork + uploadFile.getName();
                              File workFile = new File(workFilePath);
                              if(uploadFile.renameTo(workFile)){
                                  log.info("移动成功，当前文件位置：{}",workFilePath);
                                  boolean sign = false;

                                  String xmlStr = FileUtil.readToString(workFilePath);
                                  try {
                                      KeyDeliveryMessage keyDeliveryMessage = KeyDeliveryMessage.create(workFile.getName(), xmlStr);
                                      contentTitleText = keyDeliveryMessage.getCplName();
                                      compositionPlaylistId = keyDeliveryMessage.getCompositionPlaylistId();
                                      certificateThumbprint = keyDeliveryMessage.getThumbprint();
                                      if(!StringUtils.isEmpty(certificateThumbprint) && !StringUtils.isEmpty(compositionPlaylistId) && !"urn:uuid:00000000-0000-0000-0000-000000000000".equals(compositionPlaylistId)){
                                          sign = true;
                                      }
                                      log.info("KeyDeliveryMessage: {}", JSONObject.toJSONString(keyDeliveryMessage));
                                  } catch (Exception e) {
                                      log.info("traverseFolder.KeyDeliveryMessage.create error workFilePath:{}", workFilePath, e);
                                  }
                                  toMoveAgain(workFilePath, contentTitleText, compositionPlaylistId, certificateThumbprint, sign);
                                  simpleRedisService.delString(fileName);
                              }else{
                                  log.error("移动到{}失败",workFilePath);
                              }
                        }else {
                            simpleRedisService.setString(fileName, getFileMd5AndDateStr(uploadFile.getAbsolutePath(),lastFileMd5AndDate[1]));
                        }
                    }
                }
            }
        } else {
            log.error(path+"目录不存在");
        }
    }

    private String getFileMd5AndNowDateStr(String path) {

        return FileUtil.getFileMd5(path)+ "_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private String getFileMd5AndDateStr(String path, String dateStr) {

        return FileUtil.getFileMd5(path)+ "_" + dateStr;
    }

    public void toMoveAgain(String workFilePath,String contentTitleText, String compositionPlaylistId, String certificateThumbprint, boolean sign){
        File file = new File(workFilePath);
        if (file.exists()) {
            String timeYMD = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String timeHH = new SimpleDateFormat("HH").format(new Date());

            String baseDirWorkYMDDownloadHH = baseDirWork + timeYMD + File.separator +Constants.DOWNLOAD + File.separator + timeHH + File.separator;
            String baseDirWorkYMDFailHH = baseDirWork + timeYMD + File.separator +Constants.FAIL + File.separator + timeHH + File.separator;
            if(sign){
                File f = new File(baseDirWorkYMDDownloadHH);
                if(!f.exists()) {
                    f.mkdirs();
                    log.info("创建文件夹{}", baseDirWorkYMDDownloadHH);
                }
                if(file.renameTo(new File(baseDirWorkYMDDownloadHH + file.getName()))){
                   log.info("移动到{}成功", baseDirWorkYMDDownloadHH + file.getName());
                    KdmInfo kdmInfo = new KdmInfo();
                    kdmInfo.setUuid(compositionPlaylistId);
                    kdmInfo.setCertificate(certificateThumbprint);
                    kdmInfo.setPath(baseDirWorkYMDDownloadHH + file.getName());
                    kdmInfo.setSource(baseDirWork.split("/")[1]);
                    Date date = new Date();
                    kdmInfo.setCreateTime(date);
                    kdmInfo.setUpdateTime(date);
                    kdmInfoService.storageKDMInfo(kdmInfo);

                    String xmlStr = FileUtil.readToString(baseDirWorkYMDDownloadHH + file.getName());
                    KdmQuerRsponse kdmQuerRsponse = new KdmQuerRsponse();
                    kdmQuerRsponse.setTitle(file.getName());
                    kdmQuerRsponse.setContent(xmlStr);
                    String redisKey = Constants.getRedisKeyQuery(compositionPlaylistId, certificateThumbprint);
                    try {
                        simpleRedisService.setStringExpire(redisKey, JSON.toJSONString(kdmQuerRsponse), 15*24*3600);
                    } catch (Exception e) {
                        log.error("UploadService.redisUtil.set key {},error",redisKey, e);
                    }
                }else{
                    log.error("移动到{}失败", baseDirWorkYMDDownloadHH + file.getName());
                }
            }else {
                File f = new File(baseDirWorkYMDFailHH);
                if(!f.exists()){
                    f.mkdirs();
                    log.info("创建文件夹{}",baseDirWorkYMDFailHH);
                }
                if(file.renameTo(new File(baseDirWorkYMDFailHH + file.getName()))){
                    log.info("移动到{}成功",baseDirWorkYMDFailHH+file.getName());
                    KdmFailInfo kdmFailInfo1 = kdmFailInfoService.selectKDMFailInfoByFileNameAndSource(file.getName(),baseDirWork.split("/")[1]);
                    if(kdmFailInfo1 == null){
                        KdmFailInfo kdmFailInfo = new KdmFailInfo();
                        kdmFailInfo.setPath(baseDirWorkYMDFailHH + file.getName());
                        kdmFailInfo.setSource(baseDirWork.split("/")[1]);
                        kdmFailInfo.setFilename(file.getName());
                        Date date = new Date();
                        kdmFailInfo.setCreateTime(date);
                        kdmFailInfo.setUpdateTime(date);
                        kdmFailInfoService.storageKDMFailInfo(kdmFailInfo);
                    }else{
                        return;
                    }
                }else{
                    log.error("移动到{}失败",baseDirWorkYMDFailHH + file.getName());
                }
            }

        } else {
            log.error("文件{}不存在!",workFilePath);
        }
    }


    public static Document parsingXml(String xmlPath){
        File file = new File(xmlPath);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            return doc;
        } catch (Exception e) {
            System.err.println("读取该xml文件失败");
            e.printStackTrace();
        }
        return null;
    }
}
