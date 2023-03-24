package com.onlineshopping.model.vo;

import java.util.List;

public class ShopsDisplayVO {
    List<ShopDisplayVO> shops;

    public ShopsDisplayVO(List<ShopDisplayVO> shops) {
        this.shops = shops;
    }

    public List<ShopDisplayVO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDisplayVO> shops) {
        this.shops = shops;
    }
}
