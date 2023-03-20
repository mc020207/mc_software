package com.onlineshopping.model.entity;

import java.io.Serializable;

public class Userinfo implements Serializable {

  private Integer userId;
  private Integer userRole;
  private String userName;
  private String userPhone;
  private String userIdCard;
  private String userEmail;
  private String userPwd;


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public Integer getUserRole() {
    return userRole;
  }

  public void setUserRole(Integer userRole) {
    this.userRole = userRole;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public String getUserIdCard() {
    return userIdCard;
  }

  public void setUserIdCard(String userIdCard) {
    this.userIdCard = userIdCard;
  }


  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }


  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }

}
