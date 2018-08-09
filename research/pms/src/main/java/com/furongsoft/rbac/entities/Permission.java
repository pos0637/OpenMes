package com.furongsoft.rbac.entities;

import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限
 *
 * @author Alex
 */
@Entity
@Table(name = "t_sys_permission")
@Getter
@Setter
public class Permission extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;

    /**
     * 父索引
     */
    @Column(columnDefinition = "VARCHAR(32) default null COMMENT '父索引'")
    private String parentId;

    /**
     * 名称
     */
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(64) COMMENT '名称'")
    private String name;

    /**
     * 类型
     */
    @Column(nullable = false, columnDefinition = "INT(1) default 0 COMMENT '类型:0 模块, 1 数据'")
    private Integer type;

    /**
     * 路径
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '路径'")
    private String path;

    /**
     * 状态
     */
    @Column(nullable = false, columnDefinition = "INT(1) COMMENT '状态:0 启用, 1 禁用'")
    private Integer state;

    /**
     * 优先级
     */
    @Column(columnDefinition = "INT(4) default 0 COMMENT '优先级'")
    private Integer priority;

    /**
     * 图标
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '图标'")
    private String iconId;

    /**
     * 图标
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iconId", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Fetch(FetchMode.JOIN)
    @RestResource(exported = false)
    private Resource icon;

    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}