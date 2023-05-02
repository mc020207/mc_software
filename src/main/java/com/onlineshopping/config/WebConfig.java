package com.onlineshopping.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    public String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "/src/main/resources/static/img";
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + getSavePath() + "/");
    }
}

