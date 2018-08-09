package com.furongsoft.openmes.research.pms.configurations;

import com.furongsoft.base.entities.RestResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
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

    @Getter
    class PagedResourcesResponse<T> extends PagedResources<Resource<T>> {
        private int code;
        private int errno;
        private String message;
        private String newToken;

        PagedResourcesResponse(PagedResources<Resource<T>> pagedResources) {
            super(pagedResources.getContent(), pagedResources.getMetadata(), pagedResources.getLinks());
            this.code = HttpStatus.OK.value();
            this.errno = (HttpStatus.OK.value() == code) ? 0 : -1;
            this.message = null;
            this.newToken = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Bean
    public <T> ResourceProcessor<ResourceSupport> resourceProcessor() {
        return new ResourceProcessor<ResourceSupport>() {
            @Override
            public ResourceSupport process(ResourceSupport resource) {
                if (resource instanceof PagedResources) {
                    return new PagedResourcesResponse<T>((PagedResources<Resource<T>>) resource);
                } else {
                    return new RestResponse(HttpStatus.OK, null, resource);
                }
            }
        };
    }
}
