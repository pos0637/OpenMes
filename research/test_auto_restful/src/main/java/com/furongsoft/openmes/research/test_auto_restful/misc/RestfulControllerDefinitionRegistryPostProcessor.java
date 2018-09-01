package com.furongsoft.openmes.research.test_auto_restful.misc;

import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulController;
import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulService;
import com.furongsoft.openmes.research.test_auto_restful.services.BaseService;
import lombok.AllArgsConstructor;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.List;

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
        // Object service = applicationContext.getBean(beanDefinition.getBeanClassName());
    }

    private void generateController(BeanDefinitionRegistry beanDefinitionRegistry, BeanDefinition beanDefinition) {
        String beanName = "Gen$$" + beanDefinition.getBeanClassName() + "$$Controller";
        Class beanClass;
        RestfulService annotation;

        try {
            beanClass = getClass().getClassLoader().loadClass(beanDefinition.getBeanClassName());
            annotation = (RestfulService) beanClass.getAnnotation(RestfulService.class);
        } catch (ClassNotFoundException e) {
            return;
        }

        Class<?> clazz = new ByteBuddy()
                .subclass(BaseController.class)
                .annotateType(new ControllerImpl())
                .annotateType(new RestfulControllerImpl(beanClass))
                .annotateType(new RequestMappingImpl(annotation.basePath()))
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

    @Setter
    public class BaseController {
        private BaseService baseService;

        public BaseController() {
            Annotation annotation = getClass().getAnnotation(RestfulController.class);
            baseService = (BaseService) applicationContext.getBean(((RestfulController) annotation).service());
        }

        @RequestMapping
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

    @AllArgsConstructor
    class RestfulControllerImpl implements RestfulController {
        private final Class service;

        @Override
        public Class service() {
            return service;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return RestfulController.class;
        }
    }

    @AllArgsConstructor
    class RequestMappingImpl implements RequestMapping {
        private final String value;

        @Override
        public String name() {
            return "";
        }

        @Override
        public String[] value() {
            return new String[]{value};
        }

        @Override
        public String[] path() {
            return new String[]{value};
        }

        @Override
        public RequestMethod[] method() {
            return new RequestMethod[0];
        }

        @Override
        public String[] params() {
            return new String[0];
        }

        @Override
        public String[] headers() {
            return new String[0];
        }

        @Override
        public String[] consumes() {
            return new String[0];
        }

        @Override
        public String[] produces() {
            return new String[0];
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return RequestMapping.class;
        }
    }
}
