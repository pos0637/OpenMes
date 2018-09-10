package com.furongsoft.openmes.research.pms.services.user;

import com.furongsoft.core.entities.BaseEntity;
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
}