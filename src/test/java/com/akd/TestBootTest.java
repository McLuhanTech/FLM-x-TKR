package com.akd;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Run.class)
@Slf4j
public class TestBootTest {
    @Autowired
    StringEncryptor stringEncryptor;//密码解码器自动注入

    @Test
    public void test() {
        System.out.println(stringEncryptor.decrypt("UFjika/bHEmD11ygjYYi4Hd8LfrLCCW9"));
    }
}
