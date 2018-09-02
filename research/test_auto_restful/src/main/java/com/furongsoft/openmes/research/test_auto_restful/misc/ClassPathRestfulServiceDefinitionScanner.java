package com.furongsoft.openmes.research.test_auto_restful.misc;

import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulService;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 自动Restful服务扫描器
 *
 * @author Alex
 */
public class ClassPathRestfulServiceDefinitionScanner extends ClassPathBeanDefinitionScanner {
    ClassPathRestfulServiceDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 扫描自动Restful服务
     *
     * @param basePackages 基础包名
     */
    public List<BeanDefinition> doRestfulServiceScan(String... basePackages) {
        List<BeanDefinition> result = new LinkedList<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = super.findCandidateComponents(basePackage);
            result.addAll(beanDefinitions);
        }

        return result;
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RestfulService.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(RestfulService.class.getName());
    }
}
