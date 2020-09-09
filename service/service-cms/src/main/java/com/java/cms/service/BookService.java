package com.java.cms.service;

import com.java.cms.dao.BookDao;
import com.java.cms.entity.Book;
import com.java.cms.vo.BookQuery;
import com.java.commonutils.jpa.base.service.BaseService;
import com.java.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService extends BaseService<BookDao,Book> {

    @Autowired
    private BookDao bookDao;

    /**
     * 查询所有书籍
     * @return
     */
    public List<Book> findBook() {
        return bookDao.findAll();
    }

    /**
     * 带条件的分页查询
     * @param bookQuery 封装查询条件
     * @param pageNo 当前页
     * @param pageSize 最大页
     * @return 查询到的数据
     */
    public Page<Book> findPageBook(BookQuery bookQuery,int pageNo,int pageSize){
        SimpleSpecificationBuilder simpleSpecificationBuilder = new SimpleSpecificationBuilder();

        if (null != bookQuery){
            if (!StringUtils.isEmpty(bookQuery.getTitle())){
//                                                   : 的意思是like模糊查询
                simpleSpecificationBuilder.and("title",":",bookQuery.getTitle());
            }
            if (!StringUtils.isEmpty(bookQuery.getBeginDate())){
                simpleSpecificationBuilder.and("startTime","ge",bookQuery.getBeginDate());
            }
            if (!StringUtils.isEmpty(bookQuery.getEndDate())){
                simpleSpecificationBuilder.and("startTime","lt",bookQuery.getEndDate());
            }
            if (!StringUtils.isEmpty(bookQuery.getAuthor())) {
                simpleSpecificationBuilder.and("author",":",bookQuery.getAuthor());
            }
            if (bookQuery.getSerialize() != null && (bookQuery.getSerialize().equals(1) || bookQuery.getSerialize().equals(0))) {
                simpleSpecificationBuilder.and("serialize","=",bookQuery.getSerialize());
            }
            if (bookQuery.getStatus() != null && (bookQuery.getStatus().equals(1) || bookQuery.getStatus().equals(0))){
                simpleSpecificationBuilder.and("status","=",bookQuery.getStatus());
            }
            if (bookQuery.getOriginal() != null && (bookQuery.getOriginal().equals(1) || bookQuery.getOriginal().equals(0))){
                simpleSpecificationBuilder.and("original","=",bookQuery.getOriginal());
            }
        }
        Specification<Book> specification = simpleSpecificationBuilder.getSpecification();

//        查询
       Page<Book> page = dao.findAll(specification,PageRequest.of(pageNo - 1,pageSize));
        return page;
    }

    /**
     * 使用POI导出Excel
     * @param response
     */
    public void exportExcel(HttpServletResponse response){
        try {
            //设置表头
        String[] titles = { "书名","作者","一级分类","二级分类","是否连载","字数","状态","是否收费","授权开始时间","授权结束时间","书籍简介","是否原创","书封地址","创建时间","更新时间","章节数","审核状态"};

        //1、创建workbook,对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2、在workbook中创建一个sheet
        HSSFSheet sheet = workbook.createSheet("创建一个sheet");
        //3、在sheet中添加表头，第0行
        HSSFRow row = sheet.createRow(0);
        //4、在第0行设置表头
        for (int i = 0; i < titles.length; i++) {
            row.createCell(i).setCellValue(titles[i]);
        }
        //5、查询并写入数据
        List<Book> list = this.findBook();
        if ( list != null){
            //日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //循环添加数据
            for (int i = 0; i < list.size(); i++) {
                Book book = list.get(i);

                //创建行
                //因为第一行被表头占用 所以要从第一行开始插入
                HSSFRow hssfRow = sheet.createRow(i+1);
                hssfRow.createCell(0).setCellValue(book.getTitle());
                hssfRow.createCell(1).setCellValue(book.getAuthor());
                hssfRow.createCell(2).setCellValue(book.getFirstSort());
                hssfRow.createCell(3).setCellValue(book.getSecondSort());
                hssfRow.createCell(4).setCellValue(book.getSerialize());
                hssfRow.createCell(5).setCellValue(book.getWordNumber());
                hssfRow.createCell(6).setCellValue(book.getStatus());
                hssfRow.createCell(7).setCellValue(book.getFree());
                hssfRow.createCell(8).setCellValue(sdf.format(book.getStartTime()));
                hssfRow.createCell(9).setCellValue(sdf.format(book.getEndTime()));
                hssfRow.createCell(10).setCellValue(book.getInfo());
                hssfRow.createCell(11).setCellValue(book.getOriginal());
                hssfRow.createCell(12).setCellValue(book.getImageUrl());
                hssfRow.createCell(13).setCellValue(book.getGmtCreate());
                hssfRow.createCell(14).setCellValue(book.getGmtModified());
            }
        }
        String fileName = new String(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        //6、将Excel文件输出到客户端浏览器
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用POI导入Excel操作
     * @param file
     */
    @Transactional(readOnly = false)
    public void importExcel(MultipartFile file) {
        try {
            // ## 获取文件流
            InputStream inputStream = file.getInputStream();
            // ## 声明一个Excel接口
            Workbook workbook = null;
            // ## 根据文件后缀赋予不同POIExcel对象
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                // ##处理2007版及以上Excel
                workbook = new XSSFWorkbook(inputStream);
            } else {
                // ##仅处理2003版Excel
                workbook = new HSSFWorkbook(inputStream);
            }
            // ## 得到当前excel中的sheet总数
            int numberOfSheets = workbook.getNumberOfSheets();
            // ## 循环所有sheet并得到其中内容
            for (int i = 0; i < numberOfSheets; i++) {
                // ## 每循环一次,通过当前下标依次获取sheet
                Sheet sheet = workbook.getSheetAt(i);
                // ## 通过sheet得到当前共多少行
                int rows = sheet.getPhysicalNumberOfRows();

                List<Book> list = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // ## 循环所有的行,j从1开始,因为0为第一行,第一行为表头
                for (int j = 1; j < rows; j++) {
                    // ## 每循环一次,通过当前下标一行一行的获取
                    Row row = sheet.getRow(j);
                    Book book = new Book();
                    book.setTitle(row.createCell(0).getStringCellValue());//类型为String的时候使用
                    book.setAuthor(row.createCell(1).getStringCellValue());
                    book.setFirstSort(row.createCell(2).getStringCellValue());
                    book.setSecondSort(row.createCell(3).getStringCellValue());
                    book.setSerialize((int) row.getCell(4).getNumericCellValue());//类型为int的时候使用
                    book.setWordNumber((int)row.getCell(5).getNumericCellValue());
                    book.setStatus((int)row.getCell(6).getNumericCellValue());
                    book.setFree((int)row.getCell(7).getNumericCellValue());
                    book.setStartTime(sdf.parse(row.getCell(8).getStringCellValue()));
                    book.setEndTime(sdf.parse(row.getCell(9).getStringCellValue()));
                    book.setInfo(row.createCell(10).getStringCellValue());
                    book.setOriginal((int)row.getCell(11).getNumericCellValue());
                    book.setImageUrl(row.createCell(12).getStringCellValue());
                    book.setGmtCreate(sdf.parse(row.getCell(13).getStringCellValue()));
                    book.setGmtModified(sdf.parse(row.getCell(14).getStringCellValue()));
                    list.add(book);
                }
                // ## 批量添加
                dao.saveAll(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}