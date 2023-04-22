package com.onlineshopping.model.dto;

import com.onlineshopping.model.vo.UserInfoEditFVO;

public class UserInfoEditDTO {
    private String userName;
    private String userPhone;
    private String userEmail;

    public UserInfoEditDTO(UserInfoEditFVO userInfoEditFVO) {
        this.userName = userInfoEditFVO.getUserName();
        this.userPhone = userInfoEditFVO.getUserPhone();
        this.userEmail = userInfoEditFVO.getUserEmail();
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
