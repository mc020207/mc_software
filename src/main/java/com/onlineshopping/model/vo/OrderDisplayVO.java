package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Order;
import com.onlineshopping.util.ConstantUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class OrderDisplayVO {
    private Timestamp orderDate;
    private Integer orderId;
    private Double orderMoney;
    private Integer productId;
    private String productName;
    private Integer productState;

    @Override
    public String toString() {
        return "OrderDisplayVO{" +
                "orderDate=" + orderDate +
                ", orderId=" + orderId +
                ", orderMoney=" + orderMoney +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productState=" + productState +
                '}';
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
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

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public OrderDisplayVO() {
    }

    public OrderDisplayVO(Timestamp orderDate, Integer orderId, Double orderMoney, Integer productId, String productName, Integer productState) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderMoney = orderMoney;
        this.productId = productId;
        this.productName = productName;
        this.productState = productState;
    }

    public OrderDisplayVO(Order order, Product product) {
        this.orderDate = order.getOrderDate();
        this.orderId = order.getOrderId();
        if (Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_PAY)){
            this.orderMoney = product.getProductPrice();
        }else{
            this.orderMoney=order.getOrderMoney();
        }
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productState = product.getProductState();
    }
}
