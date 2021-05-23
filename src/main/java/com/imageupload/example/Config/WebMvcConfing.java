package com.imageupload.example.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfing implements WebMvcConfigurer{
    
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { 
        "classpath:/static/",
        "classpath:/public/", "classpath:/", "classpath:/resources/", 
        };

    @Value("${custom.path.resource}")
    private String resourcePath;

    @Value("${custom.path.upload}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourcePath);
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

}
