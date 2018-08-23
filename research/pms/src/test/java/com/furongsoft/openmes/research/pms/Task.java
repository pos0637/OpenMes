package com.furongsoft.openmes.research.pms;

import com.furongsoft.base.entities.RestResponse;
import com.furongsoft.base.misc.Tracker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Task extends BaseTest {
    private static final String URL_PREFIX = "http://localhost:8080/api/v1";

    @Test
    public void testGetTasks() {
        RestResponse response = getRestTemplate().getForObject(URL_PREFIX + "/task", RestResponse.class);
        Tracker.info(response.toString());
    }

    @Test
    public void testAddTask() {
        com.furongsoft.openmes.research.pms.task.Task task = new com.furongsoft.openmes.research.pms.task.Task();
        task.setName("task");
        task.setStartTime(new Date());
        task.setEndTime(new Date());
        task.setType(0L);
        task.setState(0L);
        task.setAcceptersId(0L);
        task.setPriority(0L);
        task.setEnable(0L);

        RestResponse response = getRestTemplate().postForObject(URL_PREFIX + "/task", task, RestResponse.class);
        Tracker.info(response.toString());
    }
}
