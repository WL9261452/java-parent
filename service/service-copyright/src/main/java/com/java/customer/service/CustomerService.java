package com.java.customer.service;

import com.java.commonutils.jpa.base.service.BaseService;
import com.java.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import com.java.customer.dao.CustomerDao;
import com.java.customer.entity.Customer;
import com.java.customer.vo.CustomerQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CustomerService extends BaseService<CustomerDao, Customer> {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 查询所有客户
     * @return
     */
    public List<Customer> findCustomer() {
        return customerDao.findAll();
    }

    /**
     * 带条件的分页查询
     */
    public Page<Customer> findPageCustomer(CustomerQuery customerQuery,int pageNo,int pageSize){
        SimpleSpecificationBuilder simpleSpecificationBuilder = new SimpleSpecificationBuilder();
        if (null != customerQuery){
            if (StringUtils.isEmpty(customerQuery.getCopyrightName())){
                simpleSpecificationBuilder.and("copyrightName",":",customerQuery.getCopyrightName());
            }
        }
        Specification<Customer> specification = simpleSpecificationBuilder.getSpecification();
        Page<Customer> page = dao.findAll(specification, PageRequest.of(pageNo-1,pageSize));
        return page;
    }
}
