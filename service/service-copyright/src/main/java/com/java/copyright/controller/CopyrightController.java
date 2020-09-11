package com.java.copyright.controller;

import com.java.commonutils.api.APICODE;
import com.java.copyright.entity.Copyright;
import com.java.copyright.service.CopyrightService;
import com.java.copyright.vo.CopyrightQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "版权引进")
@RestController
@RequestMapping("/copyright")
@CrossOrigin
public class CopyrightController {
    @Autowired
    private CopyrightService copyrightService;

    @PostMapping("saveCopyright")
    @ApiOperation(value = "添加或者版权")
    public APICODE saveCopyright(@RequestBody Copyright copyright) {
        Copyright copyright1 = copyrightService.saveOrUpdate(copyright);
        return APICODE.OK();
    }

    @GetMapping
    @ApiOperation(value = "查询所有内容")
    public APICODE findAll(){
       List<Copyright> copyrightList = copyrightService.findAll();
       return APICODE.OK().data("items",copyrightList);
    }

    @PostMapping("{pageNo}/{pageSize}")
    @ApiOperation(value = "根据条件进行分页查询")
    public APICODE findPageCopyright(@RequestBody(required = false) CopyrightQuery copyrightQuery,
                                     @PathVariable(name = "pageNo") int pageNo,@PathVariable(name = "pageSize") int pageSize){
        //调用service方法查询
        Page<Copyright> page = copyrightService.findPageCopyright(copyrightQuery,pageNo,pageSize);
        //总数 count()
        long totalElements = page.getTotalElements();
        //得到查询结果集合
        List<Copyright> list = page.getContent();
        return APICODE.OK().data("total",totalElements).data("items",list);
    }

    @DeleteMapping("deleteById/{copyrightId}")
    @ApiOperation(value = "根据Id删除版权")
    public APICODE deleteCopyrightById(@PathVariable(name = "copyrightId") String copyrightId){
        copyrightService.removeById(copyrightId);
        return APICODE.OK();
    }

    @ApiOperation(value = "根据id得到Copyright信息")
    @GetMapping("getCopyrightById/{copyrightId}")
    public APICODE getCopyrightById(@PathVariable(name = "copyrightId")String copyrightId){
        Copyright copyright = copyrightService.getById(copyrightId);
        return APICODE.OK().data("copyright",copyright);
    }
}

