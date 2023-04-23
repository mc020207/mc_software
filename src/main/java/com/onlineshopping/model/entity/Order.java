package com.onlineshopping.model.entity;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Order implements Serializable {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer orderState;
    private Timestamp orderDate;
    private Double orderMoney;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", orderState=" + orderState +
                ", orderDate=" + orderDate +
                ", orderMoney=" + orderMoney +
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

    public Order(Integer orderId, Integer userId, Integer productId, Integer orderState, Timestamp orderDate, Double orderMoney) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderState = orderState;
        this.orderDate = orderDate;
        this.orderMoney = orderMoney;
    }

    public Order() {
    }
}
