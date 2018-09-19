package com.furongsoft.ai.facerecognition.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加人脸响应信息
 *
 * @author Alex
 */
@Getter
@Setter
public class AddFaceResponse {
    /**
     * 人脸索引
     */
    private String faceId;

    /**
     * 人脸位置
     */
    private Location location;

    /**
     * 人脸位置
     *
     * @author Alex
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Location {
        private double left;
        private double top;
        private double width;
        private double height;
        private int rotation;
    }
}
