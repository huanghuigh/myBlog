package com.huang.interceptor;

import org.apache.tomcat.util.security.Escape;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @author guangtou
 * @create 2020--02--07--12:22
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 校验用户是否登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录放行
        if (request.getSession().getAttribute("user")!=null){
            return true;
        }else{
//            否则跳转至登陆页面
            response.sendRedirect("/admin");
            return false;
        }

    }
}
