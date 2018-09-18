package com.furongsoft.rbac.entities;

import com.furongsoft.core.annotations.RestfulEntity;
import com.furongsoft.core.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色
 *
 * @author Alex
 */
@RestfulEntity
@Entity
@Table(name = "t_sys_role")
@Getter
@Setter
public class Role extends BaseEntity implements Serializable {
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
    @Column(columnDefinition = "VARCHAR(64) COMMENT '名称'")
    private String name;

    /**
     * 状态
     */
    @Column(columnDefinition = "INT(1) default 0 COMMENT '状态:0 启用, 1 禁用'")
    private Integer state;

    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}
