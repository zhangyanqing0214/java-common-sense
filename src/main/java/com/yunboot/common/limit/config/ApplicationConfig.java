package com.yunboot.common.limit.config;

import com.yunboot.common.limit.interceptor.AccessLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AccessLimitInterceptor accessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //API限流拦截
        registry.addInterceptor(accessLimitInterceptor()).addPathPatterns("/**");
    }
}
