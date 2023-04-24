package com.onlineshopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/login");
        registry.addInterceptor(new OwnerInterceptor()).addPathPatterns("/api/owner/**").addPathPatterns("/api/order/owner/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/api/admin/**");
        registry.addInterceptor(new RecordInterceptor()).addPathPatterns("/api/record/**");
    }
}

