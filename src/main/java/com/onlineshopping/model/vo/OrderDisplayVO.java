package com.onlineshopping.model.vo;

public class OrderDisplayVO {
    private String orderDate;
    private Integer orderId;
    private Double orderMoney;
    private Integer productId;
    private String productName;

    @Override
    public String toString() {
        return "OrderDisplayVO{" +
                "orderDate='" + orderDate + '\'' +
                ", orderId=" + orderId +
                ", orderMoney=" + orderMoney +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public OrderDisplayVO() {
    }

    public OrderDisplayVO(String orderDate, Integer orderId, Double orderMoney, Integer productId, String productName) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderMoney = orderMoney;
        this.productId = productId;
        this.productName = productName;
    }
}
