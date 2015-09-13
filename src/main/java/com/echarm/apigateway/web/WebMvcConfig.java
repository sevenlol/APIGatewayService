package com.echarm.apigateway.web;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

       registry
           .addResourceHandler("/js/**")
           .addResourceLocations("file:ECharmWebsite/js/");

       registry
           .addResourceHandler("/css/**")
           .addResourceLocations("file:ECharmWebsite/css/");

       registry
           .addResourceHandler("/images/**")
           .addResourceLocations("file:ECharmWebsite/images/");

       registry
           .addResourceHandler("/app/**")
           .addResourceLocations("file:ECharmWebsite/app/");

       registry
           .addResourceHandler("/fonts/**")
           .addResourceLocations("file:ECharmWebsite/fonts/");

       registry.addResourceHandler("/ECharmWebsite/**")
//           .addResourceLocations("file:///C:/Users/stephen/Projects/ECharmWebsite/");
           .addResourceLocations("file:ECharmWebsite/");
//           .setCachePeriod(3600)
//           .resourceChain(true)
//           .addResolver(new GzipResourceResolver())
//           .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
//           .addTransformer(new AppCacheManifestTransformer());


       super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/ECharmWebsite/index.html");
    }

//    @Bean
//    public Filter shallowEtagHeaderFilter() {
//      return new ShallowEtagHeaderFilter();
//    }

}