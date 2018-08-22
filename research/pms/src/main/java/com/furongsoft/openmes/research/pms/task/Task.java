package com.furongsoft.openmes.research.pms.task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 任务
 *
 * @author chenfuqian
 */
@Entity
@Table(name = "t_pms_task")
@Getter
@Setter
public class Task {
    /**
     * 自增序号
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "任务名称不能为空")
    @Length(max = 128, message = "任务名称长度在128字符之内")
    private String name;

    /**
     * 负责人组索引
     */
    private Long ownerId;

    /**
     * 预期开始时间
     */
    @NotNull(message = "预期开始时间不能为空")
    private Date startTime;

    /**
     * 实际开始时间
     */
    private Date realStartTime;

    /**
     * 预期结束时间
     */
    @NotNull(message = "预期结束时间不能为空")
    private Date endTime;

    /**
     * 实际结束时间
     */
    private Date realEndTime;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private Long state;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    private Long type;

    /**
     * 描述
     */
    private String description;

    /**
     * 附件组索引
     */
    private Long attachmentsId;

    /**
     * 验收人组索引
     */
    @NotNull(message = "验收人不能为空")
    private Long acceptersId;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空")
    private Long priority;

    /**
     * 删除标志位
     */
    private Long enable;

    /**
     * 负责人
     */
    @Transient
    private List<Long> ownersIdList;

    /**
     * 验收人
     */
    @Transient
    private List<Long> acceptersIdList;

    /**
     * 需求
     */
    @Transient
    private List<Long> requirementIdList;

    /**
     * 附件
     */
    @Transient
    private List<Long> attachmentsIdList;
}
