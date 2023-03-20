package com.onlineshopping.model.vo;

public class DemoVO {
    String userName;
    String userRole;
    String what;

    public DemoVO(String userName, String userRole, String what) {
        this.userName = userName;
        this.userRole = userRole;
        this.what = what;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
