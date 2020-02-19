package com.maple.authority.controller;

import com.maple.authority.utils.JwtUtils;
import com.maple.authority.utils.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/auth/login")
    public ResultBean<String> testResultBean(@RequestParam("appId") String appId, @RequestParam("appKey") String appKey) {
        String token = JwtUtils.generateToken(appId, appKey);
        return ResultBean.getInstance().success(token);
    }

    @GetMapping("/test2")
    public ResultBean<Integer> testResultBean2() {
        String user = (String) SecurityUtils.getSubject().getPrincipal();
        return ResultBean.getInstance().success(user);
    }

    @GetMapping("/test3")
    public ResultBean<String> testResultBean3() {
        String result = "这是一个测试3";
        return ResultBean.getInstance().putCode(1003).putResult(result);
    }

    @GetMapping("/test4")
    public ResultBean<String> testResultBean4() {
        String result = "这是一个测试3";
        return ResultBean.getInstance().putCode(1003);
    }

}
