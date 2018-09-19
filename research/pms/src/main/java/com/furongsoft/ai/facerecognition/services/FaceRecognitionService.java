package com.furongsoft.ai.facerecognition.services;

import com.furongsoft.ai.facerecognition.entities.AddFaceResponse;
import com.furongsoft.ai.facerecognition.entities.SearchFaceResponse;

import java.util.List;

/**
 * 人脸识别服务
 *
 * @author Alex
 */
public interface FaceRecognitionService {
    /**
     * 添加人脸
     *
     * @param image       BASE64编码图像
     * @param userId      用户索引
     * @param information 用户信息
     * @return 添加人脸响应信息
     */
    AddFaceResponse addFace(String image, String userId, String information);

    /**
     * 删除人脸
     *
     * @param userId 用户索引
     * @param faceId 人脸索引
     * @return 是否成功
     */
    boolean deleteFace(String userId, String faceId);

    /**
     * 删除用户
     *
     * @param userId 用户索引
     * @return 是否成功
     */
    boolean deleteUser(String userId);

    /**
     * 获取人脸
     *
     * @param userId 用户索引
     * @return 人脸列表
     */
    List<String> getFaces(String userId);

    /**
     * 搜索人脸
     *
     * @param image BASE64编码图像
     * @return 人脸搜索响应信息
     */
    SearchFaceResponse searchUser(String image);

    /**
     * 认证人脸
     *
     * @param userId 用户索引
     * @param image  BASE64编码图像
     * @return 匹配度
     */
    double checkUser(String userId, String image);
}
