package com.shixun.ihome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcInterceptor extends WebMvcConfigurationSupport {
    String RESOURCE_LOCATION = "classpath:/META-INF/resources/";
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(RESOURCE_LOCATION);
        registry.addResourceHandler("/image/**").addResourceLocations("file:C:/Files/");// 1
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
}
