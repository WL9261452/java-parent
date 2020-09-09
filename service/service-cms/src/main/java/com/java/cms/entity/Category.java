package com.java.cms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 级联s分类
 */
@Data
@Entity
@Table(name = "cms_category")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid",strategy = "uuid")
    @ApiModelProperty(value = "书籍主键,采用hibernate的uuid生成32位字符串")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "parent_id")
    private String parentId;

}
