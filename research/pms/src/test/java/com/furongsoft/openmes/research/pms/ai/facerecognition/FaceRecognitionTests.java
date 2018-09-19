package com.furongsoft.openmes.research.pms.ai.facerecognition;

import com.alibaba.fastjson.JSON;
import com.furongsoft.ai.facerecognition.entities.AddFaceResponse;
import com.furongsoft.ai.facerecognition.entities.SearchFaceResponse;
import com.furongsoft.ai.facerecognition.services.FaceRecognitionService;
import com.furongsoft.core.misc.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaceRecognitionTests {
    private static final String userId = "0";
    private static final double score_threshold = 75;

    @Autowired
    private FaceRecognitionService service;

    @Test
    public void addFace() throws Exception {
        String image = FileUtils.readBase64(ResourceUtils.getFile("classpath:0.jpg").toPath());
        AddFaceResponse response = service.addFace(image, userId, null);
        assertNotNull(response);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void deleteFace() {
        List<String> list = service.getFaces(userId);
        assertNotNull(list);
        list.forEach(faceId -> assertTrue(service.deleteFace(userId, faceId)));
    }

    @Test
    public void deleteUser() {
        assertTrue(service.deleteUser(userId));
    }

    @Test
    public void getFaces() {
        List<String> list = service.getFaces(userId);
        assertNotNull(list);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void searchUser() throws Exception {
        String image = FileUtils.readBase64(ResourceUtils.getFile("classpath:0.jpg").toPath());
        SearchFaceResponse response = service.searchUser(image);
        assertNotNull(response);
        System.out.println(JSON.toJSONString(response));

        image = FileUtils.readBase64(ResourceUtils.getFile("classpath:1.jpg").toPath());
        response = service.searchUser(image);
        assertNotNull(response);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void checkUser() throws Exception {
        String image = FileUtils.readBase64(ResourceUtils.getFile("classpath:0.jpg").toPath());
        double response = service.checkUser(userId, image);
        assertTrue(response >= score_threshold);
        System.out.println(JSON.toJSONString(response));

        image = FileUtils.readBase64(ResourceUtils.getFile("classpath:1.jpg").toPath());
        response = service.checkUser(userId, image);
        assertTrue(response < score_threshold);
        System.out.println(JSON.toJSONString(response));
    }
}
