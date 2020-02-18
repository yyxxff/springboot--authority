package com.maple.authority.configuration.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import sun.awt.geom.AreaOp;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.maple.authority.configuration.BeanInitConfig.jwtProperties;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter implements Filter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        this.isLoginRequest(request,response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 查看当前Header中是否携带Authorization属性(Token)，有的话就进行登录认证授权
        if (StringUtils.isNotBlank(httpServletRequest.getHeader("Authorization"))) {

        }
        try {
            this.executeLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (StringUtils.isBlank(httpServletRequest.getHeader(jwtProperties.getTokenHeader()))) {
            return false;
        }
        return true;
    }
}
