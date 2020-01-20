package com.maple.authority.constant;

public enum ResultBeanEnum {
    ERROR(500, "failure"),
    SUCCESS(200, "success");

    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultBeanEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
