package com.onlineshopping.model.dto;

import com.onlineshopping.model.vo.UserRegisterFVO;

public class UserRegisterDTO {
    private Integer userRole;
    private String userName;
    private String userPhone;
    private String userIdCard;
    private String userEmail;
    private String userPwd;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(UserRegisterFVO userRegisterFVO) {
        this.userRole = userRegisterFVO.getUserRole();
        this.userName = userRegisterFVO.getUserName();
        this.userPhone = userRegisterFVO.getUserPhone();
        this.userIdCard = userRegisterFVO.getUserIdCard();
        this.userEmail = userRegisterFVO.getUserEmail();
        this.userPwd = userRegisterFVO.getUserPwd();
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
