package com.furongsoft.storage.services;

import com.furongsoft.core.misc.StringUtils;
import com.furongsoft.core.misc.Tracker;
import com.furongsoft.storage.entities.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * 文件存储服务
 *
 * @author Alex
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class StorageService {
    @Value("${upload.url}")
    private String uploadUrl;

    @Value("${upload.path}")
    private String uploadPath;

    private final FileRepository repository;

    @Autowired
    public StorageService(@Qualifier("com.furongsoft.storage.entities.FileRepository") FileRepository repository) {
        this.repository = repository;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件索引
     * @throws Exception 异常
     */
    public String uploadFile(MultipartFile file) throws Exception {
        com.furongsoft.storage.entities.File attachment = new com.furongsoft.storage.entities.File("", "", "", 0, "", 0);
        repository.save(attachment);

        String fileName = file.getOriginalFilename().toLowerCase();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newName = attachment.getId() + suffixName;

        // 文件名规则: 父路径 + UUID + 扩展名
        try {
            File parent = new File(uploadPath);
            File target = new File(String.format("%s/%s", parent.getAbsolutePath(), newName));
            if (!target.getParentFile().exists()) {
                if (!target.getParentFile().mkdirs()) {
                    throw new Exception();
                }
            }

            file.transferTo(target);
        } catch (IllegalStateException | IOException e) {
            Tracker.error(e);
            throw e;
        }

        attachment.setName(newName);
        attachment.setType(suffixName);
        attachment.setSize(file.getSize());
        attachment.setHash("");
        repository.save(attachment);

        return newName;
    }

    /**
     * 根据文件名称获取索引
     *
     * @param name 文件名称
     * @return 索引
     * @throws Exception 异常
     */
    public String getFileId(String name) throws Exception {
        if (StringUtils.isNullOrEmpty(name)) {
            return null;
        }

        Optional<com.furongsoft.storage.entities.File> file = repository.findOne((root, cq, cb) -> cb.equal(root.get("name"), name));
        if (!file.isPresent()) {
            throw new IllegalArgumentException();
        }

        return file.get().getId();
    }

    /**
     * 获取文件URL地址
     *
     * @param request 请求
     * @param name    文件名
     * @return 文件URL地址
     */
    public String getUrl(HttpServletRequest request, String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return "";
        }

        return String.format("%s://%s:%s%s/%s", request.getScheme(), request.getServerName(), request.getServerPort(), uploadUrl, name);
    }
}
