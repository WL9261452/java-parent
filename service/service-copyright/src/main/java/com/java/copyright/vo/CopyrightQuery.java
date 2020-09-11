package com.java.copyright.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangpeng
 * @version 1.0
 */
@Data
public class CopyrightQuery {
    @ApiModelProperty(value = "版权名")
    private String copyrightName;

    @ApiModelProperty(value = "公司名")
    private String companyName;

    @ApiModelProperty(value = "备注名")
    private String noteName;
}
