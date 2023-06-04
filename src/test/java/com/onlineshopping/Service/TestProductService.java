package com.onlineshopping.Service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.service.ProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.rowset.serial.SerialException;

@SpringBootTest
public class TestProductService {
    @Resource
    ProductService productService;
    @Test
    public void testInspectAllProducts(){
        System.out.println(productService.inspectAllProducts(1));
        try{
            System.out.println(productService.inspectAllProducts(100000));
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(productService.inspectAllProducts(-1));
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
