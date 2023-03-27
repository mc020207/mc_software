package com.onlineshopping.Service;

import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestShopService {
    @Resource
    ShopService shopService;

    @Test
    public void testApproveShopRegister(){
        for (int i=30;i<40;i++)
            shopService.approveShopRegister(i);
    }

    @Test
    public void testRejectShopRegister(){
        for (int i=51;i<52;i++)
            shopService.rejectShopRegister(i);
    }

    @Test
    public void testInspect(){
        System.out.println(shopService.inspect(1));
    }

    @Test
    public void testInspectDetail(){
        System.out.println(shopService.inspectDetail(4));
    }


    @Test
    public void testDisplay(){
        try{
            shopService.display(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(shopService.display(1));
    }

    @Test
    public void testDisplayDetail(){
        try{
            shopService.displayDetail(45);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            shopService.displayDetail(55);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(shopService.displayDetail(35));
    }
}
