package com.skillsprint.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RedisAuthInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redisTemplate;

    public RedisAuthInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object Handler) throws Exception{
        String apiKey = request.getHeader("x-api-key");
        if(apiKey ==null || !redisTemplate.hasKey(apiKey)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Invalid or missing API Key\"}");
            return false;
        }
        return true;
    }
}
