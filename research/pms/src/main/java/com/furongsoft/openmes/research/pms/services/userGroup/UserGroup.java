package com.furongsoft.openmes.research.pms.services.userGroup;

import com.furongsoft.base.annotations.RestfulEntity;
import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户组
 *
 * @author Alex
 */
@RestfulEntity
@Entity
@Table(name = "t_pms_user_group")
@Getter
@Setter
public class UserGroup extends BaseEntity implements Serializable {
    /**
     * 索引
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;
}
