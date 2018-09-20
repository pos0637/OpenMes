package com.furongsoft.storage.controllers;

import com.furongsoft.core.entities.RestResponse;
import com.furongsoft.core.entities.UploadFileResponse;
import com.furongsoft.core.misc.Tracker;
import com.furongsoft.storage.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 *
 * @author Alex
 */
@Controller
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public RestResponse upload(@NonNull @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return new RestResponse(HttpStatus.BAD_REQUEST);
        }

        try {
            String uuid = storageService.uploadFile(file);
            Tracker.file("upload: " + uuid);
            return new UploadFileResponse(HttpStatus.OK, uuid);
        } catch (Exception e) {
            Tracker.error(e);
            return null;
        }
    }
}
