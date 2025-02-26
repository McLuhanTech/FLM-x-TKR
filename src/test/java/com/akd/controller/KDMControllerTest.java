package com.akd.controller;

import com.akd.Run;
import com.akd.common.result.ResultBody;
import com.akd.entity.request.KdmQuerRequest;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Run.class)
@Slf4j
public class KDMControllerTest {
    @Autowired
    private KdmController kdmController;
    @Test
    public void select(){
        KdmQuerRequest kdmQuerRequest1 = new KdmQuerRequest();
        kdmQuerRequest1.setUuid("urn:uuid:edc6470d-6da0-4839-ba86-2f6fab559a7a");
        kdmQuerRequest1.setCertificate("VZf7nSTdxMscCwhxK+97bi97ZLI=");
        ResultBody query = kdmController.query(kdmQuerRequest1);
        String jsonString = JSONObject.toJSONString(query);
        System.out.println(jsonString);
    }

    public static void main(String[] args) {

    }
}
