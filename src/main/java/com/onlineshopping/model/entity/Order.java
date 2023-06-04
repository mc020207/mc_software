package com.onlineshopping.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer orderState;
    private Timestamp orderDate;
    private Double orderMoney;
    private Integer productNum;
    private Integer orderGroupId;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", orderState=" + orderState +
                ", orderDate=" + orderDate +
                ", orderMoney=" + orderMoney +
                ", productNum=" + productNum +
                ", orderGroupId=" + orderGroupId +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public Order(Integer orderId, Integer userId, Integer productId, Integer orderState, Timestamp orderDate, Double orderMoney, Integer productNum, Integer orderGroupId) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderState = orderState;
        this.orderDate = orderDate;
        this.orderMoney = orderMoney;
        this.productNum = productNum;
        this.orderGroupId = orderGroupId;
    }
}
