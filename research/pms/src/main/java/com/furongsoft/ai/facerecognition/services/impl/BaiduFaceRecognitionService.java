package com.furongsoft.ai.facerecognition.services.impl;

import com.baidu.aip.face.AipFace;
import com.furongsoft.ai.facerecognition.entities.AddFaceResponse;
import com.furongsoft.ai.facerecognition.entities.SearchFaceResponse;
import com.furongsoft.ai.facerecognition.services.FaceRecognitionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
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
public class BaiduFaceRecognitionService implements InitializingBean, FaceRecognitionService {
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

    @Override
    public AddFaceResponse addFace(String image, String userId, String information) {
        HashMap<String, String> options = new HashMap<>(1);
        options.put("user_info", information);

        JSONObject res = client.addUser(image, "BASE64", groupId, userId, options);
        if (res == null) {
            return null;
        }

        if (!res.get("error_code").toString().equals("0")) {
            return null;
        }

        JSONObject result = res.getJSONObject("result");
        if (result == null) {
            return null;
        }

        AddFaceResponse response = new AddFaceResponse();
        response.setFaceId(result.get("face_token").toString());

        JSONObject location = result.getJSONObject("location");
        response.setLocation(new AddFaceResponse.Location(
                Double.parseDouble(location.get("left").toString()),
                Double.parseDouble(location.get("top").toString()),
                Double.parseDouble(location.get("width").toString()),
                Double.parseDouble(location.get("height").toString()),
                Integer.parseInt(location.get("rotation").toString())
        ));

        return response;
    }

    @Override
    public boolean deleteFace(String userId, String faceId) {
        JSONObject res = client.faceDelete(userId, groupId, faceId, null);
        return (res != null) && res.get("error_code").toString().equals("0");
    }

    @Override
    public boolean deleteUser(String userId) {
        JSONObject res = client.deleteUser(groupId, userId, null);
        return (res != null) && res.get("error_code").toString().equals("0");
    }

    @Override
    public List<String> getFaces(String userId) {
        JSONObject res = client.faceGetlist(userId, groupId, null);
        if (res == null) {
            return null;
        }

        if (!res.get("error_code").toString().equals("0")) {
            return null;
        }

        JSONObject result = res.getJSONObject("result");
        if (result == null) {
            return null;
        }

        JSONArray array = result.getJSONArray("face_list");
        if (array == null) {
            return null;
        }

        List<String> list = new LinkedList<>();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject object = array.getJSONObject(i);
            list.add(object.get("face_token").toString());
        }

        return list;
    }

    @Override
    public SearchFaceResponse searchUser(String image) {
        JSONObject res = client.search(image, "BASE64", groupId, null);
        if (res == null) {
            return null;
        }

        if (!res.get("error_code").toString().equals("0")) {
            return null;
        }

        JSONObject result = res.getJSONObject("result");
        if (result == null) {
            return null;
        }

        SearchFaceResponse response = new SearchFaceResponse();
        JSONArray array = result.getJSONArray("user_list");
        if (array != null) {
            List<SearchFaceResponse.Face> list = new LinkedList<>();
            for (int i = 0; i < array.length(); ++i) {
                JSONObject object = array.getJSONObject(i);
                list.add(new SearchFaceResponse.Face(
                        object.get("user_id").toString(),
                        Double.parseDouble(object.get("score").toString())
                ));
            }
            response.setFaces(list);
        }

        return response;
    }

    @Override
    public double checkUser(String userId, String image) {
        HashMap<String, String> options = new HashMap<>(1);
        options.put("user_id", userId);

        JSONObject res = client.search(image, "BASE64", groupId, options);
        if (res == null) {
            return 0;
        }

        if (!res.get("error_code").toString().equals("0")) {
            return 0;
        }

        JSONObject result = res.getJSONObject("result");
        if (result == null) {
            return 0;
        }

        JSONArray array = result.getJSONArray("user_list");
        if ((array != null) && (array.length() > 0)) {
            JSONObject object = array.getJSONObject(0);
            return Double.parseDouble(object.get("score").toString());
        }

        return 0;
    }

    @Override
    public void afterPropertiesSet() {
        client = new AipFace(appId, apiKey, secretKey);
        client.setConnectionTimeoutInMillis(timeout);
        client.setSocketTimeoutInMillis(timeout);
    }
}
