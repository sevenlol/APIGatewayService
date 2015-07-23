package com.echarm.apigateway.web;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

       registry.addResourceHandler("/ECharmWebsite/**")
//           .addResourceLocations("file:///C:/Users/stephen/Projects/ECharmWebsite/")
           .addResourceLocations("file:ECharmWebsite/")
           .setCachePeriod(3600)
           .resourceChain(true)
           .addResolver(new GzipResourceResolver())
           .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
//           .addTransformer(new AppCacheManifestTransformer());


       super.addResourceHandlers(registry);
    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
      return new ShallowEtagHeaderFilter();
    }

}