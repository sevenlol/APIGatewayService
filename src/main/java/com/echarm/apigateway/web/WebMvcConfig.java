package com.echarm.apigateway.web;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    private static final String PRODUCTION_PROFILE = "production";
    private static final String DEVELOPMENT_PROFILE = "development";

    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        boolean[] boolList = isProdDev(getProfileList(environment));

        boolean isProduction = boolList[0];
        boolean isDevelopment = boolList[1];

        if (isProduction) {
            setProductionResource(registry);
        }

        if (isDevelopment) {
            setDevelopmentResource(registry);
        }

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
        boolean[] boolList = isProdDev(getProfileList(environment));

        boolean isProduction = boolList[0];
        boolean isDevelopment = boolList[1];


        if (isProduction) {
            registry.addViewController("/").setViewName("forward:/ECharmWebsite/build/dist/index.html");
        }

        if (isDevelopment) {
            registry.addViewController("/").setViewName("forward:/ECharmWebsite/index.html");
        }
    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
      return new ShallowEtagHeaderFilter();
    }

    private void setProductionResource(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/js/**")
            .addResourceLocations("file:ECharmWebsite/build/dist/js/");

        registry
            .addResourceHandler("/css/**")
            .addResourceLocations("file:ECharmWebsite/build/dist/css/");

        registry
            .addResourceHandler("/images/**")
            .addResourceLocations("file:ECharmWebsite/build/dist/images/")
            .setCachePeriod(31536000)
            .resourceChain(true)
            .addResolver(new GzipResourceResolver())
            .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
            .addTransformer(new AppCacheManifestTransformer());

        registry
            .addResourceHandler("/app/**")
            .addResourceLocations("file:ECharmWebsite/build/dist/app/")
            .resourceChain(true)
            .addResolver(new GzipResourceResolver())
            .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
            .addTransformer(new AppCacheManifestTransformer());
    }

    private void setDevelopmentResource(ResourceHandlerRegistry registry) {

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
    }

    private String[] getProfileList(Environment env) {
        final String[] DEFAULT_PROFILE_LIST = { DEVELOPMENT_PROFILE };

        if (environment == null || environment.getActiveProfiles() == null) {
            return DEFAULT_PROFILE_LIST;
        }
        return environment.getActiveProfiles();
    }

    private boolean[] isProdDev(String[] profileList) {
        boolean isProduction = false;
        boolean isDevelopment = false;
        for (String profile : profileList) {
            System.out.println(profile);
            if (PRODUCTION_PROFILE.equalsIgnoreCase(profile)) {
                isProduction = true;
            }
            if (DEVELOPMENT_PROFILE.equalsIgnoreCase(profile)) {
                isDevelopment = true;
            }
        }
        boolean[] boolList = new boolean[2];
        boolList[0] = isProduction;
        boolList[1] = isDevelopment;
        return boolList;
    }
}