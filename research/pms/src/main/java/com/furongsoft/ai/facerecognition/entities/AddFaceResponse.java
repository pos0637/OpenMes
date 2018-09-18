package com.furongsoft.ai.facerecognition.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<Location> locations;

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
