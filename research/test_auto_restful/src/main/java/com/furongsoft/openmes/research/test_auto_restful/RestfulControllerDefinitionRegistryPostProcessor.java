package com.furongsoft.openmes.research.test_auto_restful;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.List;

@Configuration
public class RestfulControllerDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathRestfulControllerDefinitionScanner scanner = new ClassPathRestfulControllerDefinitionScanner(beanDefinitionRegistry);
        scanner.setResourceLoader(applicationContext);
        List<BeanDefinition> beanDefinitions = scanner.doRestfulControllerScan("com.furongsoft");
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

    private void generateBean(BeanDefinitionRegistry beanDefinitionRegistry, List<BeanDefinition> beanDefinitions) {
        if (beanDefinitions == null) {
            return;
        }

        beanDefinitions.forEach(beanDefinition -> {
            Class<?> clazz = new ByteBuddy()
                    .subclass(BaseController.class)
                    .annotateType(new ControllerImpl())
                    .name(beanDefinition.getBeanClassName())
                    .make()
                    .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded();

            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(clazz);
            definition.setScope("singleton");
            definition.setLazyInit(false);
            definition.setAutowireCandidate(true);
            beanDefinitionRegistry.registerBeanDefinition(beanDefinition.getBeanClassName(), definition);
        });
    }
}
