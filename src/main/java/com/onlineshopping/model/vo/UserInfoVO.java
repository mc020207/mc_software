package com.onlineshopping.model.vo;

import com.onlineshopping.model.dto.UserInfoDTO;

public class UserInfoVO {
    private Integer userRole;
    private String userName;
    private String userPhone;
    private String userIdCard;
    private String userEmail;

    public UserInfoVO(UserInfoDTO userInfoDTO) {
        this.userRole = userInfoDTO.getUserRole();
        this.userName = userInfoDTO.getUserName();
        this.userPhone = userInfoDTO.getUserPhone();
        this.userIdCard = userInfoDTO.getUserIdCard();
        this.userEmail = userInfoDTO.getUserEmail();
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
}
