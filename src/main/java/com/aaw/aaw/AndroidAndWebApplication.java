package com.aaw.aaw;

import com.aaw.aaw.U_interceper.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@SpringBootApplication
public class AndroidAndWebApplication  implements WebMvcConfigurer {


    public static void main(String[] args) {
        SpringApplication.run(AndroidAndWebApplication.class, args);
    }
    @Bean
    public interceptor customInterceptor() {
        return new interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .addPathPatterns("/aaw/**"); // 拦截所有路径 ;// 拦截以 / 开头的所有路径
    }


}
