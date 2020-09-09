package com.java.cms.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.java.cms.entity.Book;
import com.java.cms.service.BookService;
import com.java.cms.vo.BookData;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<BookData> {

    private BookService bookService;

    public ExcelListener() {
    }

    public ExcelListener(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 一行一行读取数据时调用的方法
     * @param bookData
     * @param analysisContext
     */
    @Override
    public void invoke(BookData bookData, AnalysisContext analysisContext) {
        Book book = new Book();
        BeanUtils.copyProperties(bookData,book);//进行一次复制
        // 保存
        bookService.saveOrUpdate(book);
    }


    /**
     * 读取完成后执行的方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
