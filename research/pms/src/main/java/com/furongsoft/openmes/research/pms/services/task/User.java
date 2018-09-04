package com.furongsoft.openmes.research.pms.services.task;

import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户
 *
 * @author Alex
 */
@Entity
@Table(name = "t_sys_user")
@Getter
@Setter
public class User extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;

    /**
     * 登录账户
     */
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(32) COMMENT '登录账户'")
    private String userName;
}