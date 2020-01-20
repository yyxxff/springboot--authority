package com.maple.authority.controller;

import com.maple.authority.utils.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResultBean<String> testResultBean() {
        String result = "这是一个测试";
        return ResultBean.getInstance().success(result);
    }

    @GetMapping("/test2")
    public ResultBean<Integer> testResultBean2() {
        Integer result = 1;
        return ResultBean.getInstance().error(result);
    }

    @GetMapping("/test3")
    public ResultBean<String> testResultBean3() {
        String result = "这是一个测试3";
        return ResultBean.getInstance().putCode(1003);
    }

    @GetMapping("/test4")
    public ResultBean<String> testResultBean4() {
        String result = "这是一个测试3";
        return ResultBean.getInstance().putCode(1003);
    }

}
