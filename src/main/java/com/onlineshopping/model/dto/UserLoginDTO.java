package com.onlineshopping.model.dto;

import com.onlineshopping.model.vo.UserLoginFVO;

public class UserLoginDTO {
    private String userName;
    private String userPwd;

    public UserLoginDTO(UserLoginFVO userLoginFVO) {
        this.userName = userLoginFVO.getUserName();
        this.userPwd = userLoginFVO.getUserPwd();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
