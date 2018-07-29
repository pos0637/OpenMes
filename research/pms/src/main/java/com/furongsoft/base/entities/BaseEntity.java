package com.furongsoft.base.entities;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类型
 *
 * @author Alex
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    /**
     * 创建用户
     */
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "VARCHAR(32) COMMENT '创建用户'")
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "DATETIME COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后修改用户
     */
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "VARCHAR(32) COMMENT '最后修改用户'")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifyUser;

    /**
     * 最后修改时间
     */
    @JsonIgnore
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifyTime;
}
