package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopDetailVO {
    Integer shopId;
    String shopName;
    List<String> productNames;
    String shopIntro;
    String shopAddr;
    Double shopRegisterFund;
    Date shopRegisterDate;

    public ShopDetailVO(Shop shop, List<Product> products) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        productNames = new ArrayList<>();
        for (Product product : products)
            productNames.add(product.getProductName());
        this.shopIntro = shop.getShopIntro();
        this.shopAddr = shop.getShopAddr();
        this.shopRegisterFund = shop.getShopRegisterFund();
        this.shopRegisterDate = shop.getShopRegisterDate();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public String getShopIntro() {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public Double getShopRegisterFund() {
        return shopRegisterFund;
    }

    public void setShopRegisterFund(Double shopRegisterFund) {
        this.shopRegisterFund = shopRegisterFund;
    }

    public Date getShopRegisterDate() {
        return shopRegisterDate;
    }

    public void setShopRegisterDate(Date shopRegisterDate) {
        this.shopRegisterDate = shopRegisterDate;
    }
}
