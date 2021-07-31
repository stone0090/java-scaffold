//package com.example.demo.web.config;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * @author stone
// * @date 2021/07/28
// */
//public class LoginFilter extends AccessControlFilter {
//
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpRequest = WebUtils.toHttp(request);
//        HttpServletResponse httpResponse = WebUtils.toHttp(response);
//        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//            httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,HEAD,PUT,PATCH");
//            httpResponse.setHeader("Access-Control-Max-Age", "36000");
//            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            httpResponse.setHeader("Access-Control-Allow-Headers",
//                httpRequest.getHeader("Access-Control-Request-Headers"));
//            httpResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        return false;
//    }
//
//}
//
