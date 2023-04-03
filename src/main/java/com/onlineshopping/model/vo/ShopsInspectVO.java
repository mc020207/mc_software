package com.onlineshopping.model.vo;

import java.util.List;

public class ShopsInspectVO {
    private List<ShopInspectVO> shops;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ShopsInspectVO{" +
                "shops=" + shops +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ShopInspectVO> getShops() {
        return shops;
    }

    public void setShops(List<ShopInspectVO> shops) {
        this.shops = shops;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ShopsInspectVO() {
    }

    public ShopsInspectVO(List<ShopInspectVO> shops, Integer totalNumber) {
        this.shops = shops;
        this.totalNumber = totalNumber;
    }
}
