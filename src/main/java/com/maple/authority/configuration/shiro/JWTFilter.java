package com.maple.authority.configuration.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.maple.authority.configuration.BeanInitConfig.jwtProperties;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter implements Filter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (StringUtils.isBlank(httpServletRequest.getHeader(jwtProperties.getTokenHeader()))) {
            return false;
        }
        return true;
    }
}
