package com.echarm.apigateway.web;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

       registry.addResourceHandler("/ECharmWebsite/**")
           //.addResourceLocations("file:///C:/Users/stephen/Projects/ECharmWebsite/");
           .addResourceLocations("file:ECharmWebsite/");

       super.addResourceHandlers(registry);
    }

}