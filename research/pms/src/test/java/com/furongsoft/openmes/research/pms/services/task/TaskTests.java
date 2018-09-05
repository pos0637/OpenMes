package com.furongsoft.openmes.research.pms.services.task;

import com.alibaba.fastjson.JSON;
import com.furongsoft.base.entities.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTests {
    private static final String URL = "http://localhost:8080/api/v1/tasks";

    @Test
    public void getTasks() {
        RestResponse response = new RestTemplate().getForObject(URL, RestResponse.class);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void addTask() {
        Task task = new Task();
        task.setName("new Task");
        task.setState(0L);
        task.setType(0L);
        task.setPriority(0L);
        task.setEnable(0);
        task.setStartTime(new Date());
        task.setEndTime(new Date());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(JSON.toJSONString(task), headers);

        RestResponse response = new RestTemplate().postForObject(URL, request, RestResponse.class);
        System.out.println(JSON.toJSONString(response));
    }
}
