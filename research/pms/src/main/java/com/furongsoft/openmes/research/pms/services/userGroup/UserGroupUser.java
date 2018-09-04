package com.furongsoft.openmes.research.pms.services.userGroup;

import com.furongsoft.base.annotations.RestfulEntity;
import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户组用户
 *
 * @author Alex
 */
@RestfulEntity
@Entity
@Table(name = "t_pms_user_group_user")
@Getter
@Setter
public class UserGroupUser extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;

    /**
     * 用户组索引
     */
    @Column(nullable = false, columnDefinition = "BIGINT(20) COMMENT '用户组索引'")
    private long userGroupId;

    /**
     * 用户索引
     */
    @Column(nullable = false, columnDefinition = "BIGINT(20) COMMENT '用户索引'")
    private long userId;
}
