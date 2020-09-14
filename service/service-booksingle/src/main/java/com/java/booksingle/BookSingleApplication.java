package com.java.booksingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient//开启服务注册功能
@SpringBootApplication
@EnableJpaAuditing//开启自动填充注解
@ComponentScan(basePackages = {"com.java"})
public class BookSingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSingleApplication.class,args);
    }

}
