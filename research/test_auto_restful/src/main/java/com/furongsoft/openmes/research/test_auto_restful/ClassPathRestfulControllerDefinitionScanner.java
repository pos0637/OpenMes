package com.furongsoft.openmes.research.test_auto_restful;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 自动Restful控制器扫描器
 *
 * @author Alex
 */
public class ClassPathRestfulControllerDefinitionScanner extends ClassPathBeanDefinitionScanner {
    ClassPathRestfulControllerDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 扫描自动Restful控制器
     *
     * @param basePackages 基础包名
     */
    public List<BeanDefinition> doRestfulControllerScan(String... basePackages) {
        List<BeanDefinition> result = new LinkedList<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = super.findCandidateComponents(basePackage);
            result.addAll(beanDefinitions);
        }

        return result;
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RestfulController.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(RestfulController.class.getName());
    }
}
