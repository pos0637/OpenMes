package com.furongsoft.openmes.research.test_auto_restful.misc;

import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulService;
import com.furongsoft.openmes.research.test_auto_restful.repositories.BaseRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Configuration
public class RestfulControllerDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathRestfulServiceDefinitionScanner scanner = new ClassPathRestfulServiceDefinitionScanner(beanDefinitionRegistry);
        scanner.setResourceLoader(applicationContext);
        List<BeanDefinition> beanDefinitions = scanner.doRestfulServiceScan("com.furongsoft");
        generateBean(beanDefinitionRegistry, beanDefinitions);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public class BaseController {
        @RequestMapping("/bar")
        @ResponseBody
        public String bar() {
            return "bar";
        }
    }

    class ControllerImpl implements Controller {
        @Override
        public String value() {
            return "";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Controller.class;
        }
    }

    class RepositoryImpl implements Repository {
        @Override
        public String value() {
            return "";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Repository.class;
        }
    }

    private void generateBean(BeanDefinitionRegistry beanDefinitionRegistry, List<BeanDefinition> beanDefinitions) {
        if (beanDefinitions == null) {
            return;
        }

        beanDefinitions.forEach(beanDefinition -> {
            generateService(beanDefinitionRegistry, beanDefinition);
            generateController(beanDefinitionRegistry, beanDefinition);
        });
    }

    private void generateService(BeanDefinitionRegistry beanDefinitionRegistry, BeanDefinition beanDefinition) {
        try {
            Class<?> clazz = getClass().getClassLoader().loadClass(beanDefinition.getBeanClassName());
            RestfulService annotation = clazz.getAnnotation(RestfulService.class);
            System.out.println(annotation.vo().getName());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateController(BeanDefinitionRegistry beanDefinitionRegistry, BeanDefinition beanDefinition) {
        String beanName = "Gen" + beanDefinition.getBeanClassName();
        Class<?> clazz = new ByteBuddy()
                .subclass(BaseController.class)
                .annotateType(new ControllerImpl())
                .name(beanName)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(clazz);
        definition.setScope("singleton");
        definition.setLazyInit(false);
        definition.setAutowireCandidate(true);
        beanDefinitionRegistry.registerBeanDefinition(beanName, definition);
    }
}
