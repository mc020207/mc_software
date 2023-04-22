package com.onlineshopping.model.vo;

public class UserPwdEditFVO {
    String userOldPwd;
    String userNewPwd;

    public UserPwdEditFVO() {
    }

    public String getUserOldPwd() {
        return userOldPwd;
    }

    public void setUserOldPwd(String userOldPwd) {
        this.userOldPwd = userOldPwd;
    }

    public String getUserNewPwd() {
        return userNewPwd;
    }

    public void setUserNewPwd(String userNewPwd) {
        this.userNewPwd = userNewPwd;
    }
}
