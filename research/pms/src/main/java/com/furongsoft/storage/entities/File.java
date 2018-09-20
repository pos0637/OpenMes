package com.furongsoft.storage.entities;

import com.furongsoft.core.annotations.RestfulEntity;
import com.furongsoft.core.entities.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文件
 *
 * @author Alex
 */
@RestfulEntity
@Entity
@Table(name = "t_sys_file")
@Getter
@Setter
@NoArgsConstructor
public class File extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(32) COMMENT 'UUID'")
    private String id;

    /**
     * 文件名称
     */
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255) COMMENT '文件名称'")
    private String name;

    /**
     * 类型
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '类型'")
    private String type;

    /**
     * URL
     */
    @Column(columnDefinition = "VARCHAR(1024) COMMENT 'URL'")
    private String url;

    /**
     * 文件大小
     */
    @Column(columnDefinition = "BIGINT(20) COMMENT '文件大小'")
    private Long size;

    /**
     * 文件哈希
     */
    @Column(columnDefinition = "VARCHAR(128) COMMENT '文件哈希'")
    private String hash;

    /**
     * 状态
     */
    @Column(columnDefinition = "INT(1) COMMENT '状态:0 启用, 1 禁用'")
    private Integer state;

    public File(String name) {
        this.name = name;
    }

    public File(String name, String type, String url, long size, String hash, Integer state) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.size = size;
        this.hash = hash;
        this.state = state;
    }
}
