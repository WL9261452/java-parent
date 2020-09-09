package com.java.cms.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 设置表头和属性
 */
@Data
public class BookData {

        @ExcelProperty(value = "书名",index = 0)
        private String title;

        @ExcelProperty(value = "作者",index = 1)
        private String author;

        @ExcelProperty(value = "一级分类",index = 2)
        private String firstSort;

        @ExcelProperty(value = "二级分类",index = 3)
        private String secondSort;

        @ExcelProperty(value = "是否连载",index = 4)
        private Integer serialize;

        @ExcelProperty(value = "字数",index = 5)
        private Integer wordNumber;

        @ExcelProperty(value = "状态",index = 6)
        private Integer status;

        @ExcelProperty(value = "是否收费",index = 7)
        private Integer free;

        @ExcelProperty(value = "书封地址",index = 8)
        private String imageUrl;

        @ExcelProperty(value = "授权开始时间",index = 9)
        private Date startTime;

        @ExcelProperty(value = "授权结束时间",index = 10)
        private Date endTime;

        @ExcelProperty(value = "书籍简介",index = 11)
        private String info;

        @ExcelProperty(value = "是否原创",index = 12)
        private Integer original;

        @ExcelProperty(value = "创建时间",index = 13)
        private Date gmtCreate;

        @ExcelProperty(value = "更新时间",index = 14)
        private Date gmtModified;
}



