package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Order;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.util.ConstantUtil;

import java.sql.Timestamp;
import java.util.Objects;

public class OrderDisplayVO {
    private Timestamp orderDate;
    private Integer orderGroupId;
    private Integer orderId;
    private double orderMoney;
    private Integer productId;
    private String productName;
    private Integer productNum;
    private Integer productState;
    private String shopName;
    private Integer orderState;

    @Override
    public String toString() {
        return "OrderDisplayVO{" +
                "orderDate=" + orderDate +
                ", orderGroupId=" + orderGroupId +
                ", orderId=" + orderId +
                ", orderMoney=" + orderMoney +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productNum=" + productNum +
                ", productState=" + productState +
                ", shopName='" + shopName + '\'' +
                ", orderState=" + orderState +
                '}';
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
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

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public OrderDisplayVO() {
    }

    public OrderDisplayVO(Timestamp orderDate, Integer orderGroupId, Integer orderId, double orderMoney, Integer productId, String productName, Integer productNum, Integer productState, String shopName, Integer orderState) {
        this.orderDate = orderDate;
        this.orderGroupId = orderGroupId;
        this.orderId = orderId;
        this.orderMoney = orderMoney;
        this.productId = productId;
        this.productName = productName;
        this.productNum = productNum;
        this.productState = productState;
        this.shopName = shopName;
        this.orderState = orderState;
    }

    public OrderDisplayVO(Order order, Product product) {
        this.orderDate = order.getOrderDate();
        this.orderId = order.getOrderId();
        if (Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_PAY)|| Objects.equals(order.getOrderState(), ConstantUtil.ORDER_ADD_TO_CART)) {
            this.orderMoney = product.getProductPrice();
        } else {
            this.orderMoney = order.getOrderMoney();
        }
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productState = product.getProductState();
        this.orderGroupId=order.getOrderGroupId();
        this.productNum=order.getProductNum();
        this.orderState=order.getOrderState();
    }
}
