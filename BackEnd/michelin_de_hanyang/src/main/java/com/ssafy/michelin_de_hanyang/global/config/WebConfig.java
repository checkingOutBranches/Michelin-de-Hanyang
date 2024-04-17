package com.ssafy.michelin_de_hanyang.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://j10a609.p.ssafy.io")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH", "TRACE")
                .allowedHeaders("Content-Type", "Authorization")
                .exposedHeaders("*")
                .allowCredentials(true);
    }
}

