package com.java.booksingle.controller;

import com.java.booksingle.entity.BookSingle;
import com.java.booksingle.service.BookSingleService;
import com.java.booksingle.vo.BookSingleQuery;
import com.java.commonutils.api.APICODE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "书单管理")
@RestController
@RequestMapping("/booksingle")
public class BookSingleController {

    @Autowired
    private BookSingleService bookSingleService;

    @ApiOperation(value = "查询所有书单信息")
    @GetMapping
    public APICODE findBookSingle(){
        List<BookSingle> bookSingle = bookSingleService.findBookSingle();
        return APICODE.OK().data("items",bookSingle);
    }

    @PostMapping("{pageNo}/{pageSize}")
    @ApiOperation(value = "根据条件进行分页查询")
    public APICODE findPageBookSingle(@RequestBody(required = false) BookSingleQuery bookSingleQuery,
                                     @PathVariable(name = "pageNo") int pageNo,@PathVariable(name = "pageSize") int pageSize){
        //调用service方法查询
        Page<BookSingle> page = bookSingleService.findPageBookSingle(bookSingleQuery,pageNo,pageSize);
        //总数 count()
        long totalElements = page.getTotalElements();
        //得到查询结果集合
        List<BookSingle> list = page.getContent();
        return APICODE.OK().data("total",totalElements).data("items",list);
    }


    @ApiOperation(value = "书单添加")
    @PostMapping("saveBookSingle")
    public APICODE saveBookSingle(@RequestBody BookSingle bookSingle){
        bookSingleService.saveOrUpdate(bookSingle);
        return APICODE.OK();
    }

    @ApiOperation(value = "根据Id查询书单")
    @GetMapping("getBookSingleById/{booksingleId}")
    public APICODE getBookSingleById(@PathVariable String booksingleId){
        BookSingle bookSingle = bookSingleService.getById(booksingleId);
        return APICODE.OK().data("customer",bookSingle);
    }

    @DeleteMapping("deleteById/{booksingleId}")
    @ApiOperation(value = "根据Id删除书单")
    public APICODE deleteBookSingleById(@PathVariable(name = "booksingleId") String booksingleId){
        bookSingleService.removeById(booksingleId);
        return APICODE.OK();
    }

    @ApiOperation(value = "修改书单信息")
    @PutMapping("updateBookSingle")
    public APICODE updateBookSingle(@RequestBody BookSingle bookSingle){
        bookSingleService.saveOrUpdate(bookSingle);
        return APICODE.OK();
    }

}
