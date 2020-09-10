package com.java.copyright.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangeng
 * @version 1.0
 */
@Data
@Entity
@Table(name = "copyright_management")
@EntityListeners(AuditingEntityListener.class)
public class Copyright {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid", strategy = "uuid")
    @ApiModelProperty(value = "版权主键，采用hibernate的uuid生成32位字符串")
    private String id ;

    /** 客户外键 */
    @Column(name = "copyright_id")
    @ApiModelProperty(value = "客户外键")
    private Integer copyrightId;


    @Column(name = "copyright_name")
    @ApiModelProperty(value = "版权名")
    private String copyrightName;

    @Column(name = "company_name")
    @ApiModelProperty(value = "公司名")
    private String companyName;

    @Column(name = "note_name")
    @ApiModelProperty(value = "备注名")
    private String noteName;

    @Column(name = "cooperation_status")
    @ApiModelProperty(value = "合作状态1-有效，0-失效")
    private Integer cooperationStatus;

//    @Column(name = "starttime_cooperation")
//    @ApiModelProperty(value = "合作开始时间")
//    private Date starttimeCooperation;
//
//
//    @Column(name = "endtime_cooperation")
//    @ApiModelProperty(value = "合作结束时间")
//    private Date endtimeCooperation;

    /** 版权简介 */
    @Column(name = "copyright_introduce")
    @ApiModelProperty(value = "版权简介")
    private String copyrightIntroduce;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @Column(name = "created_time",nullable = false,updatable = false)
    @ApiModelProperty(value = "创建时间", example = "2020-12-12 9:00:00")
    private Date createdTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    @Column(name = "updated_time",nullable = false,insertable = false)
    @ApiModelProperty(value = "修改时间", example = "2020-12-21 9:00:00")
    private Date updatedTime;


//    /** 乐观锁 */
//    private Integer revision ;
//    /** 创建人 */
//    private String createdBy ;
//    /** 更新人 */
//    private String updatedBy ;



}
