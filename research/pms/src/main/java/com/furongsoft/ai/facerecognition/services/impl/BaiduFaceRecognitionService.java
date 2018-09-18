package com.furongsoft.ai.facerecognition.services.impl;

import com.baidu.aip.face.AipFace;
import com.furongsoft.ai.facerecognition.entities.AddFaceResponse;
import com.furongsoft.ai.facerecognition.entities.SearchFaceResponse;
import com.furongsoft.ai.facerecognition.services.FaceRecognitionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 百度人脸识别服务
 *
 * @author Alex
 */
@Service
public class BaiduFaceRecognitionService implements FaceRecognitionService {
    @Value("${ai.face-recognition.baidu.app-id}")
    private String appId;

    @Value("${ai.face-recognition.baidu.api-key}")
    private String apiKey;

    @Value("${ai.face-recognition.baidu.secret-key}")
    private String secretKey;

    @Value("${ai.face-recognition.baidu.group-id}")
    private String groupId;

    @Value("${ai.face-recognition.baidu.timeout}")
    private int timeout;

    private AipFace client;

    public BaiduFaceRecognitionService() {
        client = new AipFace(appId, apiKey, secretKey);
        client.setConnectionTimeoutInMillis(timeout);
        client.setSocketTimeoutInMillis(timeout);
    }

    @Override
    public AddFaceResponse addFace(String image, String userId, String information) {
        HashMap<String, String> options = new HashMap<>(1);
        options.put("user_info", information);

        JSONObject res = client.addUser(image, "BASE64", groupId, userId, options);
        if (res == null) {
            return null;
        }

        AddFaceResponse response = new AddFaceResponse();
        response.setFaceId(res.get("face_token").toString());
        JSONArray array = res.getJSONArray("location");
        if (array != null) {
            List<AddFaceResponse.Location> list = new LinkedList<>();
            for (int i = 0; i < array.length(); ++i) {
                JSONObject object = array.getJSONObject(i);
                list.add(new AddFaceResponse.Location(
                        Double.parseDouble(object.get("left").toString()),
                        Double.parseDouble(object.get("top").toString()),
                        Double.parseDouble(object.get("width").toString()),
                        Double.parseDouble(object.get("height").toString()),
                        Integer.parseInt(object.get("rotation").toString())
                ));
            }
            response.setLocations(list);
        }

        return response;
    }

    @Override
    public boolean deleteFaces(String userId, String faceId) {
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public List<String> getFaces(String userId) {
        return null;
    }

    @Override
    public SearchFaceResponse searchUser(String image) {
        return null;
    }

    @Override
    public double checkUser(String userId, String image) {
        return 0;
    }
}
