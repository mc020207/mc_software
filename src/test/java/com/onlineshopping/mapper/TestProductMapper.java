package com.onlineshopping.mapper;


import com.onlineshopping.model.entity.Product;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestProductMapper {
    @Resource
    ProductMapper productMapper;

    @Test
    public void testInsertProduct(){
        for (int i = 0; i<30; i++){
            productMapper.insertProduct(new Product(null,3,"mc"+ Integer.toString(i)));
        }
    }


    @Test
    public void testSelectProductsBySingleAttr(){
        System.out.println(productMapper.selectProductsBySingleAttr("shopId",3));
    }

    @Test
    public void testDeleteProductsByShopId(){
        productMapper.deleteProductsByShopId(3);
    }

    @Test
    public void testCountProductsByShopId(){
        System.out.println(productMapper.countProductsByShopId(23));
    }
}
