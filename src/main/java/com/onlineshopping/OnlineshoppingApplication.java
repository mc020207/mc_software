package com.onlineshopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.onlineshopping.mapper")
public class OnlineshoppingApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshoppingApplication.class, args);
    }

}
