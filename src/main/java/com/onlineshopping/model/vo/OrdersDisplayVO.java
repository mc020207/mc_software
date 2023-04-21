package com.onlineshopping.model.vo;

import java.util.List;

public class OrdersDisplayVO {
    private List<OrderDisplayVO> orders;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "OrdersDisplayVO{" +
                "orders=" + orders +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<OrderDisplayVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDisplayVO> orders) {
        this.orders = orders;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public OrdersDisplayVO() {
    }

    public OrdersDisplayVO(List<OrderDisplayVO> orders, Integer totalNumber) {
        this.orders = orders;
        this.totalNumber = totalNumber;
    }
}
