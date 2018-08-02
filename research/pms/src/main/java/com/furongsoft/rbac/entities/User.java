package com.furongsoft.rbac.entities;

import com.baomidou.mybatisplus.annotations.TableName;
import com.furongsoft.base.entities.BaseEntity;
import com.furongsoft.base.misc.JpaUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author Alex
 */
@Entity
@Table(name = "t_sys_user")
@TableName("t_sys_user")
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
     * 密码
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(64) COMMENT '密码'")
    private String password;

    /**
     * 盐值
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(64) COMMENT '盐值'")
    private String salt;

    /**
     * 状态
     */
    @Column(columnDefinition = "INT(1) default 0 COMMENT '状态:0 启用, 1 禁用'")
    private Integer state;

    /**
     * 姓名
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '姓名'")
    private String name;

    /**
     * 性别
     */
    @Column(columnDefinition = "INT(10) COMMENT '性别'")
    private Integer sex;

    /**
     * 年龄
     */
    @Column(columnDefinition = "INT(10) COMMENT '年龄'")
    private Integer age;

    /**
     * 生日
     */
    @Column(columnDefinition = "DATETIME COMMENT '生日'")
    private Date birthday;

    /**
     * 头衔
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '头衔'")
    private String title;

    /**
     * 头衔
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '头衔'")
    private String title2;

    /**
     * 头衔
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '头衔'")
    private String title3;

    /**
     * 工作单位
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '工作单位'")
    private String company;

    /**
     * 工作单位
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '工作单位'")
    private String company2;

    /**
     * 工作单位
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '工作单位'")
    private String company3;

    /**
     * 工作地址
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '工作地址'")
    private String businessAddress;

    /**
     * 工作地址
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '工作地址'")
    private String businessAddress2;

    /**
     * 住宅地址
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '住宅地址'")
    private String address;

    /**
     * 住宅地址
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '住宅地址'")
    private String address2;

    /**
     * 电子邮件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '电子邮件'")
    private String email;

    /**
     * 电子邮件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '电子邮件'")
    private String email2;

    /**
     * 电子邮件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '电子邮件'")
    private String email3;

    /**
     * 网站
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '网站'")
    private String web;

    /**
     * 网站
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '网站'")
    private String web2;

    /**
     * 网站
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '网站'")
    private String web3;

    /**
     * 移动电话
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '移动电话'")
    private String mobile;

    /**
     * 移动电话
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '移动电话'")
    private String mobile2;

    /**
     * 固定电话
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '固定电话'")
    private String telephone;

    /**
     * 固定电话
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '固定电话'")
    private String telephone2;

    /**
     * 社交软件账号
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '社交软件账号'")
    private String snsAccount;

    /**
     * 社交软件账号
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '社交软件账号'")
    private String snsAccount2;

    /**
     * 社交软件账号
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '社交软件账号'")
    private String snsAccount3;

    /**
     * 国家
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '国家'")
    private String country;

    /**
     * 省份/州
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '省份/州'")
    private String province;

    /**
     * 市/县
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '市/县'")
    private String city;

    /**
     * 区/镇
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '区/镇'")
    private String town;

    /**
     * 街道
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '街道'")
    private String street;

    /**
     * 邮编
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '邮编'")
    private String zip;

    /**
     * 证件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '证件'")
    private String identification;

    /**
     * 证件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '证件'")
    private String identification2;

    /**
     * 证件
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '证件'")
    private String identification3;

    /**
     * 头像
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '头像'")
    private String iconUrl;

    /**
     * 照片
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '照片'")
    private String pictureUrl;

    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}