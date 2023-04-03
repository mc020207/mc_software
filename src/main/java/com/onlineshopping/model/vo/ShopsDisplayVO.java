package com.onlineshopping.model.vo;

import java.util.List;

public class ShopsDisplayVO {
    List<ShopDisplayVO> shops;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ShopsDisplayVO{" +
                "shops=" + shops +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ShopDisplayVO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDisplayVO> shops) {
        this.shops = shops;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ShopsDisplayVO() {
    }

    public ShopsDisplayVO(List<ShopDisplayVO> shops, Integer totalNumber) {
        this.shops = shops;
        this.totalNumber = totalNumber;
    }
}
