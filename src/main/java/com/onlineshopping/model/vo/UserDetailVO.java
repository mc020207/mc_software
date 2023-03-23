package com.onlineshopping.model.vo;

import com.onlineshopping.model.dto.UserDetailDTO;
import com.onlineshopping.model.entity.User;

public class UserDetailVO {
    private Integer userRole;
    private String userName;
    private String userPhone;
    private String userIdCard;
    private String userEmail;

    public UserDetailVO(UserDetailDTO userDetailDTO) {
        this.userRole = userDetailDTO.getUserRole();
        this.userName = userDetailDTO.getUserName();
        this.userPhone = userDetailDTO.getUserPhone();
        this.userIdCard = userDetailDTO.getUserIdCard();
        this.userEmail = userDetailDTO.getUserEmail();
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
