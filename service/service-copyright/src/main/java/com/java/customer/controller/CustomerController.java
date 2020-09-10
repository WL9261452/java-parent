package com.java.customer.controller;

import com.java.commonutils.api.APICODE;
import com.java.customer.entity.Customer;
import com.java.customer.service.CustomerService;
import com.java.customer.vo.CustomerQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "版权客户管理")
@RestController
@CrossOrigin
@RequestMapping("/copyright/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "查询所有客户")
    @GetMapping("findCustomer")
    public APICODE findCustomer(){
        List<Customer> customers = customerService.findCustomer();
        return APICODE.OK().data("items",customers);
    }

    @ApiOperation(value = "根据条件进行分页查询")
    @PostMapping("{pageNo}/{pageSize}")
    public APICODE findPageCustomer(@RequestBody(required = false) CustomerQuery customerQuery,
                                    @PathVariable(value = "pageNo") int pageNo,
                                    @PathVariable(value = "pageSize") int pageSize){
        // 调用service的方法，进行分页查询
        Page<Customer> page = customerService.findPageCustomer(customerQuery,pageNo,pageSize);
        long totalElements = page.getTotalElements();
        List<Customer> list = page.getContent();//当前页查询的数据集合
        return APICODE.OK().data("total",totalElements).data("items",list);
    }

    @ApiOperation(value = "客户添加")
    @PostMapping("saveCustomer")
    public APICODE saveCustomer(@RequestBody Customer customer){
          customerService.saveOrUpdate(customer);
          return APICODE.OK();
    }

    @ApiOperation(value = "根据Id查询客户")
    @GetMapping("getCustomerById/{customerId}")
    public APICODE getCustomerById(@PathVariable String customerId){
        Customer customer = customerService.getById(customerId);
        return APICODE.OK().data("customer",customer);
    }

    @ApiOperation(value = "根据Id修改客户信息")
    @PutMapping("updateCustomer")
    public APICODE updateCustomer(@RequestBody Customer customer){
        customerService.saveOrUpdate(customer);
        return APICODE.OK();
    }

    @DeleteMapping("deleteCustomer/{customerId}")
    public APICODE deleteCustomer(String customerId){
        customerService.removeById(customerId);
        return APICODE.OK();
    }
}


