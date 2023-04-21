package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsRecordDisplayVO {
    private List<ProductRecordDisplayVO> records;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ProductsRecordDisplayVO{" +
                "records=" + records +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ProductRecordDisplayVO> getRecords() {
        return records;
    }

    public void setRecords(List<ProductRecordDisplayVO> records) {
        this.records = records;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ProductsRecordDisplayVO() {
    }

    public ProductsRecordDisplayVO(List<ProductRecordDisplayVO> records, Integer totalNumber) {
        this.records = records;
        this.totalNumber = totalNumber;
    }
}
