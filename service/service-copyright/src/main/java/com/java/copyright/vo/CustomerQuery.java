package com.java.copyright.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerQuery {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 外键
     */
//    private Integer copyrightId;

    /**
     * 版权名
     */
    private String copyrightName;

    /**
     * 姓名
     */
    private String iname;
    /**
     * 性别
     */
    private Integer igender;
    /**
     * 出生日期
     */
    private Date idateBirth;
    /**
     * 职务
     */
    private Integer ipost;
    /**
     * 手机号码
     */
    private String iphone;
    /**
     * 座机号码
     */
    private String itelephone;
    /**
     * 邮箱
     */
    private String iemali;
    /**
     * QQ
     */
    private String iqq;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 备注
     */
    private String iremarks;

}
