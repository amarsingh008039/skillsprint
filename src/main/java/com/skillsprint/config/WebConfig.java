package com.skillsprint.config;

import com.skillsprint.security.RedisAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RedisAuthInterceptor redisAuthInterceptor;

    @Autowired
    public WebConfig(RedisAuthInterceptor redisAuthInterceptor) {
        this.redisAuthInterceptor = redisAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(redisAuthInterceptor).addPathPatterns("/api/public/**");
    }
}
