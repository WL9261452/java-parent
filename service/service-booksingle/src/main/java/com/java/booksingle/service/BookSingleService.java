package com.java.booksingle.service;

import com.java.booksingle.dao.BookSingleDao;
import com.java.booksingle.entity.BookSingle;
import com.java.booksingle.vo.BookSingleQuery;
import com.java.commonutils.jpa.base.service.BaseService;
import com.java.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookSingleService extends BaseService<BookSingleDao, BookSingle> {

    @Autowired
    private BookSingleDao bookSingleDao;

    /**
     * 查询所有书单信息
     */
    public List<BookSingle> findBookSingle(){
       return bookSingleDao.findAll();
    }

    /**
     * 带条件的分页查询
     */
    public Page<BookSingle> findPageBookSingle(BookSingleQuery bookSingleQuery, int pageNo, int pageSize){
        SimpleSpecificationBuilder simpleSpecificationBuilder = new SimpleSpecificationBuilder();
        if (null != bookSingleQuery){
            if (!StringUtils.isEmpty(bookSingleQuery.getIbooklistName())){
                simpleSpecificationBuilder.and("ibooklistName",":",bookSingleQuery.getIbooklistName());
            }
            if (!StringUtils.isEmpty(bookSingleQuery.getIbatch())){
                simpleSpecificationBuilder.and("ibatch",":",bookSingleQuery.getIbatch());
            }
        }
        Specification<BookSingle> specification = simpleSpecificationBuilder.getSpecification();
        Page<BookSingle> page = dao.findAll(specification, PageRequest.of(pageNo-1,pageSize));
        return page;
    }
}
