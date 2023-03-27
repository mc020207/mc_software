package com.onlineshopping.model.vo;

import java.util.List;

public class ShopsInspectVO {
    private List<ShopInspectVO> shops;

    @Override
    public String toString() {
        return "ShopsInspectVO{" +
                "shops=" + shops +
                '}';
    }

    public List<ShopInspectVO> getShops() {
        return shops;
    }

    public void setShops(List<ShopInspectVO> shops) {
        this.shops = shops;
    }

    public ShopsInspectVO() {
    }

    public ShopsInspectVO(List<ShopInspectVO> shops) {
        this.shops = shops;
    }
}
