package com.furongsoft.openmes.research.pms.ai.facerecognition;

import com.alibaba.fastjson.JSON;
import com.furongsoft.ai.facerecognition.entities.AddFaceResponse;
import com.furongsoft.ai.facerecognition.services.FaceRecognitionService;
import com.furongsoft.core.misc.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.net.URLEncoder;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaceRecognitionTests {
    @Autowired
    private FaceRecognitionService service;

    @Test
    public void addFace() throws Exception {
        String image = FileUtils.readBase64(ResourceUtils.getFile("classpath:1.jpg").toPath());
        image = URLEncoder.encode(image, "UTF-8");
        AddFaceResponse response = service.addFace(image, "0", null);
        assertNull(response);
        System.out.println(JSON.toJSONString(response));
    }
}
