package com.furongsoft.openmes.research.pms.services.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.furongsoft.base.annotations.RestfulEntity;
import com.furongsoft.base.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 任务
 *
 * @author chenfuqian
 */
@RestfulEntity
@Entity
@Table(name = "t_pms_task")
@Getter
@Setter
public class Task extends BaseEntity implements Serializable {
    /**
     * 自增序号
     */
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BIGINT(20) COMMENT '主键'")
    private long id;

    /**
     * 名称
     */
    @NotBlank(message = "任务名称不能为空")
    @Length(max = 128, message = "任务名称长度在128字符之内")
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(128) COMMENT '名称'")
    private String name;

    /**
     * 描述
     */
    @Column(columnDefinition = "VARCHAR(128) COMMENT '描述'")
    private String description;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    @Column(nullable = false, columnDefinition = "INT(1) default 0 COMMENT '状态:0 启用, 1 禁用'")
    private Long state;

    /**
     * 类型
     */
    @Column(nullable = false, columnDefinition = "INT(10) default 0 COMMENT '类型'")
    private Long type;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空")
    @Column(nullable = false, columnDefinition = "INT(10) default 0 COMMENT '优先级'")
    private Long priority;

    /**
     * 删除标志位
     */
    @Column(nullable = false, columnDefinition = "INT(1) default 0 COMMENT '删除标志位'")
    private Integer enable;

    /**
     * 从属模块
     */
    @Column(columnDefinition = "BIGINT(20) COMMENT '从属模块'")
    private Long moduleId;

    /**
     * 预期开始时间
     */
    @NotNull(message = "预期开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(nullable = false, columnDefinition = "DATETIME COMMENT '预期开始时间'")
    private Date startTime;

    /**
     * 预期结束时间
     */
    @NotNull(message = "预期结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(nullable = false, columnDefinition = "DATETIME COMMENT '预期结束时间'")
    private Date endTime;

    /**
     * 实际开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(columnDefinition = "DATETIME COMMENT '实际开始时间'")
    private Date realStartTime;

    /**
     * 实际结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(columnDefinition = "DATETIME COMMENT '实际结束时间'")
    private Date realEndTime;

    /**
     * 负责人组索引
     */
    @Column(columnDefinition = "BIGINT(20) COMMENT '负责人组索引'")
    private Long ownersId;

    /**
     * 负责人组
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_pms_user_group_user",
            joinColumns = @JoinColumn(
                    name = "userGroupId",
                    referencedColumnName = "ownersId",
                    insertable = false,
                    updatable = false,
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(
                    name = "userId",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false,
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)),
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    @Fetch(FetchMode.JOIN)
    private Set<User> owners;

    /**
     * 验收人组索引
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(columnDefinition = "BIGINT(20) COMMENT '验收人组索引'")
    private Long acceptersId;

    /**
     * 附件组索引
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(columnDefinition = "BIGINT(20) COMMENT '附件组索引'")
    private Long attachmentsId;

    /**
     * 负责人索引集合
     */
    @Transient
    private List<Long> ownersIdList;

    /**
     * 验收人索引集合
     */
    @Transient
    private List<Long> accepterIdList;

    /**
     * 需求索引集合
     */
    @Transient
    private List<Long> requirementIdList;

    /**
     * 附件索引集合
     */
    @Transient
    private List<Long> attachmentsIdList;
}
