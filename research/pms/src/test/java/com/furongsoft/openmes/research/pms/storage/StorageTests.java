package com.furongsoft.openmes.research.pms.storage;

import com.alibaba.fastjson.JSON;
import com.furongsoft.core.entities.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageTests {
    private static final String URL = "http://localhost:8080/storage/upload";

    @Test
    public void upload() throws Exception {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(ResourceUtils.getFile("classpath:0.jpg")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<RestResponse> response = new RestTemplate().exchange(URL, HttpMethod.POST, new HttpEntity<>(map, headers), RestResponse.class);
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        System.out.println(JSON.toJSONString(response.getBody()));
    }
}
