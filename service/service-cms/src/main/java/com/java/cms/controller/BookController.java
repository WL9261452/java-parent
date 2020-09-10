package com.java.cms.controller;

import com.alibaba.excel.EasyExcel;
import com.java.cms.entity.Book;
import com.java.cms.listener.ExcelListener;
import com.java.cms.service.BookService;
import com.java.cms.vo.BookData;
import com.java.cms.vo.BookQuery;
import com.java.commonutils.api.APICODE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "书籍管理")
@RestController
@CrossOrigin//解决跨域
@RequestMapping("/cms/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "查询所有书籍")
    @GetMapping
    public APICODE findBook() {
//        查询
        //int o = 1/0;//设置异常
        List<Book> list = bookService.findBook();
        return APICODE.OK().data("items", list);
    }

    @ApiOperation(value = "根据条件进行分页查询")
    @PostMapping("{pageNo}/{pageSize}")
//                             @RequestBody(required = false) 查询条件可以为空
//                             @PathVariable(value = "pageSize") 为保万无一失
    public APICODE findPageBook(@RequestBody(required = false) BookQuery bookQuery,
                                @PathVariable(value = "pageNo") int pageNo,
                                @PathVariable(value = "pageSize") int pageSize) {

//        调用service的方法，进行分页查询
        Page<Book> page = bookService.findPageBook(bookQuery, pageNo, pageSize);
        long totalElements = page.getTotalElements();//获取总数
        List<Book> list = page.getContent();//当前页查询的数据集合
        return APICODE.OK().data("total", totalElements).data("items", list);
    }

    @PostMapping("saveBook")
    @ApiOperation(value = "书籍添加")
    public APICODE saveBook(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
        return APICODE.OK();
    }

    @ApiOperation(value = "根据Id查询书籍")
    @GetMapping("getBookById/{bookId}")
    public APICODE getBookById(@PathVariable String bookId) {
        Book book = bookService.getById(bookId);
        return APICODE.OK().data("book", book);
    }

    @ApiOperation(value = "修改书籍")
    @PostMapping("updateBook")
    public APICODE updateBook(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
        return APICODE.OK();
    }

    @ApiOperation(value = "根据ID对书籍进行删除")
    @DeleteMapping("deleteById/{bookId}")
    public APICODE deleteBook(@PathVariable String bookId) {
        bookService.removeById(bookId);
        return APICODE.OK();
    }

    @ApiOperation(value = "设置书籍上下架")
    @PutMapping("{bookId}/{status}")
    public APICODE upOrDownBook(@PathVariable(name = "bookId") String bookId, @PathVariable(name = "status") Integer status) {
        // ## 根据id查询书籍数据
        Book book = bookService.getById(bookId);
//        book.setId(bookId);
        book.setStatus(status);
        // ## 修改数据
        bookService.saveOrUpdate(book);
        return APICODE.OK();
    }

    @ApiOperation(value = "导出Excel", notes = "使用阿里EasyExcel开源项目完成的Excel导入操作")
    @GetMapping("exportEasyExcel")
    public void exportEasyExcel(HttpServletResponse response) {
        // ## 查询所有书籍
        List<Book> bookList = bookService.findBook();
        // ## 定义导出列表集合
        List<BookData> bookDataList = new ArrayList<>();

        for (Book book : bookList) {
            BookData bookData = new BookData();
            BeanUtils.copyProperties(book, bookData);//把book里的内容复制到bookData
            bookDataList.add(bookData);
        }

        try {

            String fileName = "booklist";

            // ## 设置响应信息
            response.reset();
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xlsx");

            //导出
            EasyExcel.write(response.getOutputStream(),BookData.class).sheet("书籍列表").doWrite(bookDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导入Excel",notes = "使用阿里云EasyExcel开源项目完成的Excel导入操作")
    @PostMapping("importEasyExcel")
    public APICODE importEasyExcel(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(),BookData.class, new ExcelListener(bookService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return APICODE.OK();
    }

//    @ApiOperation(value = "使用POI操作Excel导出",notes = "使用POI导出Excel格式的用户列表数据")
//    @GetMapping("exportExcel")
//    public APICODE exportExcel(HttpServletResponse response){
//        bookService.exportExcel(response);
//        return APICODE.OK();
//    }
//
//    @ApiOperation(value = "使用POI导入Excel操作")
//    @PostMapping("importExcel")
//    public APICODE importExcel(MultipartFile file){
//        bookService.importExcel(file);
//        return APICODE.OK();
//    }

}