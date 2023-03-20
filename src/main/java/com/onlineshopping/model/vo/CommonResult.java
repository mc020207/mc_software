package com.onlineshopping.model.vo;

public class CommonResult {
    Boolean success; //请求成功
    String message; //提示信息
    Object object; //具体返回内容

    public CommonResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
