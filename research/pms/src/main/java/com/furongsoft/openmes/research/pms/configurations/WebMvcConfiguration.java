package com.furongsoft.openmes.research.pms.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * MVC配置
 *
 * @author Alex
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Value("${resources.url}")
    private String resourcesUrl;

    @Value("${resources.path}")
    private String resouresPath;

    @Value("${upload.url}")
    private String uploadUrl;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcesUrl + "/**").addResourceLocations("file:" + resouresPath + "/");
        registry.addResourceHandler(uploadUrl + "/**").addResourceLocations("file:" + uploadPath + "/");
    }
}
