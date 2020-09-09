package com.java.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
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
@Table(name = "cms_book")
@EntityListeners(AuditingEntityListener.class)
public class Book {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid",strategy = "uuid")
    @ApiModelProperty(value = "书籍主键,采用hibernate的uuid生成32位字符串")
    private String id;

//    @Column(name = "")
//    @ApiModelProperty(value = "")
//    private Integer copyrightId;

    @Column(name = "title")
    @ApiModelProperty(value = "书名")
    private String title;

    @Column(name = "author")
    @ApiModelProperty(value = "作者")
    private String author;

    @Column(name = "first_sort")
    @ApiModelProperty(value = "一级分类")
    private String firstSort;

    @Column(name = "second_sort")
    @ApiModelProperty(value = "二级分类")
    private String secondSort;

    @Column(name = "is_serialize")
    @ApiModelProperty(value = "是否连载")
    private Integer serialize;

    @Column(name = "word_number")
    @ApiModelProperty(value = "字数")
    private Integer wordNumber;

    @Column(name = "is_status")
    @ApiModelProperty(value = "书籍状态")
    private Integer status;

    @Column(name = "is_free")
    @ApiModelProperty(value = "是否收费")
    private Integer free;

    @Column(name = "image_url")
    @ApiModelProperty(value = "书封地址")
    private String imageUrl;

    @Column(name = "start_time")
    @ApiModelProperty(value = "授权开始时间",example = "2020-02-02 9:00:00")
    private Date startTime;

    @Column(name = "end_time")
    @ApiModelProperty(value = "授权结束时间")
    private Date endTime;

    @Column(name = "info")
    @ApiModelProperty(value = "书籍简介")
    private String info;

    @Column(name = "is_original")
    @ApiModelProperty(value = "是否原创")
    private Integer original;

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
