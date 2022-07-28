package com.hteecommerce.hteapp.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCorsMapping implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("*")
            .allowedHeaders("*");
    }
    
    

}
