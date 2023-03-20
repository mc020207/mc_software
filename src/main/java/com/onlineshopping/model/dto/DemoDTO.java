package com.onlineshopping.model.dto;

public class DemoDTO {
    private String userName;
    private String userRole;

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public DemoDTO(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
