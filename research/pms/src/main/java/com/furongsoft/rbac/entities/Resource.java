package com.furongsoft.rbac.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 资源
 *
 * @author Alex
 */
@Entity
@Table(name = "t_sys_resource")
@Getter
@Setter
public class Resource extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;

    /**
     * 名称
     */
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(64) COMMENT '名称'")
    private String name;

    /**
     * 类型
     */
    @Column(nullable = false, columnDefinition = "INT(4) COMMENT '类型:0 图片, 1 文件'")
    private Integer type;

    /**
     * 路径
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '路径'")
    private String path;

    /**
     * 状态
     */
    @Column(nullable = false, columnDefinition = "INT(1) default 0 COMMENT '状态:0 启用, 1 禁用'")
    private Integer state;

    /**
     * 图标
     */
    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(32) COMMENT '图标'")
    private String icon;

    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}