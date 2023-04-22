package com.onlineshopping.model.dto;

import com.onlineshopping.model.vo.UserPwdEditFVO;

public class UserPwdEditDTO {
    String userOldPwd;
    String userNewPwd;

    public UserPwdEditDTO(UserPwdEditFVO userPwdEditFVO) {
        this.userOldPwd = userPwdEditFVO.getUserOldPwd();
        this.userNewPwd = userPwdEditFVO.getUserNewPwd();
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
