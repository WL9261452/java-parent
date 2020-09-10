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

@Data
@Entity
@Table(name = "copyright_customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid",strategy = "uuid")
    @ApiModelProperty(value = "书籍主键,采用hibernate的uuid生成32位字符串")
    private String id;
    
//    private Integer copyright;
    
    @Column(name = "copyright_name")
    @ApiModelProperty(value = "版权名")
    private String copyrightName;

    @Column(name = "iname")
    @ApiModelProperty(value = "姓名")
    private String iname;

    @Column(name = "igender")
    @ApiModelProperty(value = "性别")
    private Integer igender;

    @Column(name = "idate_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date idateBirth;

    @Column(name = "ipost")
    @ApiModelProperty(value = "职务")
    private Integer ipost;

    @Column(name = "iphone")
    @ApiModelProperty(value = "手机号码")
    private String iphone;

    @Column(name = "itelephone")
    @ApiModelProperty(value = "座机号码")
    private String itelephone;

    @Column(name = "iemali")
    @ApiModelProperty(value = "邮箱")
    private String iemali;

    @Column(name = "iqq")
    @ApiModelProperty(value = "QQ")
    private String iqq;

    @Column(name = "company_address")
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @Column(name = "iremarks")
    @ApiModelProperty(value = "备注")
    private String iremarks;


    @CreatedDate
    @Column(name = "gmt_create",nullable = false,updatable = false)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @LastModifiedDate
    @Column(name = "gmt_modified",nullable = false,insertable = false)
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

}
