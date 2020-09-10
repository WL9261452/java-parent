package com.java.copyright.service;

import com.java.commonutils.jpa.base.service.BaseService;
import com.java.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import com.java.copyright.dao.CopyrightDao;
import com.java.copyright.entity.Copyright;
import com.java.copyright.vo.CopyrightQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhangpeng
 * @version 1.0
 */
@Service
public class CopyrightService extends BaseService<CopyrightDao, Copyright> {
    @Autowired
    private CopyrightDao copyrightDao;

    public List<Copyright> findAll(){
        return copyrightDao.findAll();
    }

    /**
     * 根据条件进行分页查询
     * @param copyrightQuery
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Copyright> findPageCopyright(CopyrightQuery copyrightQuery,int pageNo,int pageSize){
        //设置查询条件
        SimpleSpecificationBuilder simpleSpecificationBuilder = new SimpleSpecificationBuilder();
        if (copyrightQuery != null){
            if (!StringUtils.isEmpty(copyrightQuery.getCopyrightName())){
                simpleSpecificationBuilder.and("copyrightName",":",copyrightQuery.getCopyrightName());
            }
            if (!StringUtils.isEmpty(copyrightQuery.getCompanyName())){
                simpleSpecificationBuilder.and("companyName",":",copyrightQuery.getCompanyName());
            }
            if (!StringUtils.isEmpty(copyrightQuery.getNoteName())){
                simpleSpecificationBuilder.and("noteName",":",copyrightQuery.getNoteName());
            }
        }
        Specification<Copyright> specification = simpleSpecificationBuilder.getSpecification();
        Page<Copyright> page = dao.findAll(specification, PageRequest.of(pageNo-1,pageSize));
        return page;
    }


}
