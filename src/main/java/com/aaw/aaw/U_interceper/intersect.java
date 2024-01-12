package com.aaw.aaw.U_interceper;
import jakarta.servlet.http.HttpServletRequest;

public class intersect {
    boolean isExcludedURI(String requestURI) {
        return requestURI.equals("/aaw/login") || requestURI.equals("/aaw/creat");//登录注册跳过拦截
    }

//    boolean isFetchRequest(HttpServletRequest request) {
//        return "fetch".equalsIgnoreCase(request.getHeader("Origin"));
//    }//拦截fetch请求
    //vip权限检测
    boolean isVip(String requestURI) {
        return requestURI.startsWith("/aaw/vip/");
    }//拦截vip权限以下用户
    //root权限检测
    public boolean isRoot(String requestURI) {
        return requestURI.startsWith("/aaw/root/");
    }//拦截非root
}
