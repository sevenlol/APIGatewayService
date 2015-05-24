package com.echarm.apigateway.web;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
       return new ResourceUrlEncodingFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

       registry.addResourceHandler("/ECharmWebsite/**")
          .addResourceLocations("file:ECharmWebsite/")
          .setCachePeriod(0);
    }

}