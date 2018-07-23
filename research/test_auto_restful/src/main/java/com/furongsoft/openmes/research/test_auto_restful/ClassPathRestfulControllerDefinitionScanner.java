package com.furongsoft.openmes.research.test_auto_restful;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 自动Restful控制器扫描器
 *
 * @author Alex
 */
public class ClassPathRestfulControllerDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public ClassPathRestfulControllerDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        beanDefinitionHolders.forEach(beanDefinitionHolder -> {
            System.out.println(beanDefinitionHolder.getBeanDefinition().getBeanClassName());
        });

        return beanDefinitionHolders;
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RestfulController.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(RestfulController.class.getName());
    }
}
