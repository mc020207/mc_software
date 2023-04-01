package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Shop;

public class ShopDisplayVO {
    Integer shopId;
    String shopName;
    String shopIntro;

    public ShopDisplayVO(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.shopIntro = shop.getShopIntro();
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

    public String getShopIntro() {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }
}
