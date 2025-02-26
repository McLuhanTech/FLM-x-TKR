package com.akd.service;


import com.akd.Run;
import com.akd.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Run.class)
@Slf4j
public class UploadServiceTest {

    @Autowired
    private Task task;

    @Test
    public void upload(){
        task.uploadFilesTask();

    }
}
