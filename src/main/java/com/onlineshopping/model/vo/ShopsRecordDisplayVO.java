package com.onlineshopping.model.vo;

import java.util.List;

public class ShopsRecordDisplayVO {
    private List<ShopRecordDisplayVO> records;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ShopsRecordDisplayVO{" +
                "records=" + records +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ShopRecordDisplayVO> getRecords() {
        return records;
    }

    public void setRecords(List<ShopRecordDisplayVO> records) {
        this.records = records;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ShopsRecordDisplayVO() {
    }

    public ShopsRecordDisplayVO(List<ShopRecordDisplayVO> records, Integer totalNumber) {
        this.records = records;
        this.totalNumber = totalNumber;
    }
}
