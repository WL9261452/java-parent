package com.java.cms.controller;

import com.java.cms.service.CategoryService;
import com.java.cms.vo.FirstCategory;
import com.java.commonutils.api.APICODE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "级联分类")
@RestController
@RequestMapping("/cms/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取一级二级分类所有分类数据")
    @GetMapping
    public APICODE getCategoryList(){
        List<FirstCategory> firstCategoryList = categoryService.getCategoryList();
        return APICODE.OK().data("items",firstCategoryList);//前端接收items
    }
}
