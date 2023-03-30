package com.onlineshopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/login");
        registry.addInterceptor(new OwnerInterceptor()).addPathPatterns("/myshop/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/inspect/**");
    }
}

