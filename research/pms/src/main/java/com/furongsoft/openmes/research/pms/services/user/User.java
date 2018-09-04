package com.furongsoft.openmes.research.pms.services.user;

import com.furongsoft.base.annotations.RestfulEntity;
import com.furongsoft.base.entities.BaseEntity;
import com.furongsoft.base.misc.JpaUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户
 *
 * @author Alex
 */
@RestfulEntity
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
    @JpaUtils.QueryField(type = JpaUtils.MatchType.like)
    private String userName;

    /**
     * 头像
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iconId", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    @Fetch(FetchMode.JOIN)
    private Resource icon;
}