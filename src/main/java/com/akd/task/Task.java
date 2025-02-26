package com.akd.task;

import com.akd.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class Task {

    @Value("${BASE_DIR_UPLOAD}")
    private String baseDirUpload;

    @Autowired
    private UploadService uploadService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void uploadFilesTask() {
        log.info("开始扫描文件夹："+baseDirUpload);
        try{
            uploadService.traverseFolder(baseDirUpload);
        }catch (Exception e){
            log.error("uploadFilesTask error", e);
        }

    }
}
