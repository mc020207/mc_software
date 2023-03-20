package com.onlineshopping.model.entity;

import java.io.Serializable;

public class Shop implements Serializable {

  private Integer shopId;
  private Integer userId;
  private String shopName;
  private String shopIntro;
  private String shopAddr;
  private Double shopRegisterFund;
  private java.sql.Date shopRegisterDate;
  private Integer shopIsOpen;


  public Integer getShopId() {
    return shopId;
  }

  public void setShopId(Integer shopId) {
    this.shopId = shopId;
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }


  public String getShopIntro() {
    return shopIntro;
  }

  public void setShopIntro(String shopIntro) {
    this.shopIntro = shopIntro;
  }


  public String getShopAddr() {
    return shopAddr;
  }

  public void setShopAddr(String shopAddr) {
    this.shopAddr = shopAddr;
  }


  public Double getShopRegisterFund() {
    return shopRegisterFund;
  }

  public void setShopRegisterFund(Double shopRegisterFund) {
    this.shopRegisterFund = shopRegisterFund;
  }


  public java.sql.Date getShopRegisterDate() {
    return shopRegisterDate;
  }

  public void setShopRegisterDate(java.sql.Date shopRegisterDate) {
    this.shopRegisterDate = shopRegisterDate;
  }


  public Integer getShopIsOpen() {
    return shopIsOpen;
  }

  public void setShopIsOpen(Integer shopIsOpen) {
    this.shopIsOpen = shopIsOpen;
  }

}
