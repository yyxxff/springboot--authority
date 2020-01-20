package com.maple.authority.utils;

import com.maple.authority.constant.ResultBeanEnum;
import lombok.Data;

@Data
public class ResultBean<T> {

    private static ResultBean singleton = null;

    private Integer code;

    private String message;

    private T result;


    public ResultBean() {
    }

    public static ResultBean getInstance() {
        singleton = new ResultBean();
        return singleton;
    }

    public <T> ResultBean<T> putCode(Integer code) {
        singleton.setCode(code);
        return (ResultBean<T>) singleton;
    }

    public <T> ResultBean<T> putMessage(String message) {
        singleton.setMessage(message);
        return (ResultBean<T>) singleton;
    }


    public ResultBean<T> putResult(T result) {
        singleton.setResult(result);
        return (ResultBean<T>) singleton;
    }

    public ResultBean<T> success(T result) {
        singleton.setCode(ResultBeanEnum.SUCCESS.getStatus());
        singleton.setMessage(ResultBeanEnum.SUCCESS.getMsg());
        singleton.setResult(result);
        return (ResultBean<T>) singleton;
    }

    public ResultBean<T> error(T result) {
        singleton.setCode(ResultBeanEnum.ERROR.getStatus());
        singleton.setMessage(ResultBeanEnum.ERROR.getMsg());
        singleton.setResult(result);
        return (ResultBean<T>) singleton;
    }
}
