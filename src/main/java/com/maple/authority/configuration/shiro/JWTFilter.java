package com.maple.authority.configuration.shiro;

import com.maple.authority.exception.AuthorityException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.awt.geom.AreaOp;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.maple.authority.configuration.BeanInitConfig.jwtProperties;


/**
 * 1.鉴权拦截先走isAccessAllowed方法，判断是否需要鉴权逻辑
 * 2.如果需要鉴权，走executeLogin方法，这个方法执行到getSubject(request, response).login(token);这段代码，会跳转到shiro中CustomRealm的doGetAuthenticationInfo逻辑
 * 3.是否需要鉴权，可以直接在ShiroConfig中shiroFilterFactoryBean中添加
 * 注意：在这个方法中拦截器只校验token，具体的人员权限验证在CustomRealm的doGetAuthenticationInfo中处理
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter implements Filter {

    /**
     * 这里重写此方法，可以更加灵活的判断什么时候需要鉴权
     * 现在是header中有Authorization且有值时需要鉴权
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        return StringUtils.isNotBlank(authorization);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        log.info("判断用户是否想要登录x：{}", authorization);
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Boolean isLoginAttempt = isLoginAttempt(request, response);
        if (isLoginAttempt) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                try {
                    response401(request, response);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
        httpResponse.getWriter().close();
    }
}
