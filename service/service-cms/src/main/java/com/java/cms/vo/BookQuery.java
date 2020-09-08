package com.java.cms.vo;

import lombok.Data;

@Data
public class BookQuery {

    private String title;

    //开始时间
    private String beginDate;

    //结束时间
    private String endDate;

    //作者
    private String author;

    private String pcode;

    private String code;

    //连载
    private Integer serialize;

    //状态
    private Integer status;

    //原创
    private Integer original;
}
