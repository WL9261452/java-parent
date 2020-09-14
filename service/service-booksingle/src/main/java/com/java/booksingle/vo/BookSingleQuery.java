package com.java.booksingle.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookSingleQuery {

    @ApiModelProperty(value = "书单名称")
    private String ibooklistName;

    @ApiModelProperty(value = "批次名称")
    private String ibatch;

}
