package com.furongsoft.ai.facerecognition.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 人脸搜索响应信息
 *
 * @author Alex
 */
@Getter
@Setter
public class SearchFaceResponse {
    /**
     * 人脸信息列表
     */
    private List<Face> faces;

    /**
     * 人脸信息
     *
     * @author Alex
     */
    @Getter
    @Setter
    public static class Face {
        /**
         * 用户索引
         */
        private String userId;

        /**
         * 匹配度
         */
        private double score;
    }
}
