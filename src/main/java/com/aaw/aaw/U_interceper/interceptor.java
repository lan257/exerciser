package com.aaw.aaw.U_interceper;

import com.aaw.aaw.H_tool.jwt;
import com.aaw.aaw.O_solidObjects.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component

public class interceptor implements HandlerInterceptor{
    intersect is=new intersect();
    long begin,end,i;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            // 如果是OPTIONS请求，就直接返回true，跳过后续的拦截器和控制器方法
            return true;
        }
        if (is.isExcludedURI(request.getRequestURI())) {
            // 放行不需要拦截的 URI
            log.info("URI excluded, allowing access.");
            return true;
        }

        String token = request.getHeader("Authorization");
        log.info("jwt令牌："+token);
        log.info("请求开始，URL: "+ request.getRequestURI());
        user u;
        jwt key = new jwt();
        try {
            u = key.getJwt(token);
            request.setAttribute("jwtInfo", u);
            if (is.isRoot(request.getRequestURI()) && u.getType() >0) {
                System.out.println("被拦截了");
                sendResponse(response, HttpServletResponse.SC_FORBIDDEN, "User blocked: Insufficient permissions");
                return false;
            }
            if (is.isVip(request.getRequestURI()) && u.getType() >1) {
                System.out.println("非vip被拦截了");
                sendResponse(response, HttpServletResponse.SC_FORBIDDEN, "User blocked: You are not subscribed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("校验失败");
            sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return false;
        }
        System.out.println("JWT Token: " + token);
        return true;
    }

    // 在 sendResponse 方法中添加拦截原因的消息
    private void sendResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("message", message));
    }

    @Override
    public void postHandle(jakarta.servlet.http.HttpServletRequest request,
                           jakarta.servlet.http.HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        begin=System.nanoTime();
        // 在处理器方法执行后但在视图渲染之前执行的逻辑
        log.info("postHandle 方法执行，URL: "+ request.getRequestURI());
    }

    @Override
    public void afterCompletion(jakarta.servlet.http.HttpServletRequest request,
                                jakarta.servlet.http.HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        end=System.nanoTime();
        i=end-begin;
        double x=i/1000000.0;
        // 在整个请求处理完成后执行的逻辑
        log.info("afterCompletion 方法执行，URL: "+ request.getRequestURI());
        log.info(request.getRequestURI()+"执行耗时:"+x+"ms");
    }

    //登录检测
}


