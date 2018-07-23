package com.furongsoft.openmes.research.test_auto_restful;

import com.itranswarp.compiler.JavaStringCompiler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    static final String JAVA_SOURCE_CODE = ""
            + "package on.the.fly;                                            "
            + "import org.springframework.stereotype.Controller;              "
            + "import org.springframework.web.bind.annotation.RequestMapping; "
            + "import org.springframework.web.bind.annotation.ResponseBody;   "
            + "@Controller                                                    "
            + "public class Foo {                                             "
            + "    @RequestMapping(\"/bar\")                                  "
            + "    @ResponseBody                                              "
            + "    public String bar() {                                      "
            + "        return \"bar\";                                        "
            + "    }                                                          "
            + "}";

    private void generateBean(BeanDefinitionRegistry beanDefinitionRegistry, List<BeanDefinition> beanDefinitions) {
        JavaStringCompiler compiler = new JavaStringCompiler();
        try {
            Map<String, byte[]> results = compiler.compile("Foo.java", JAVA_SOURCE_CODE);
            Class<?> clazz = compiler.loadClass("on.the.fly.Foo", results);

            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(clazz);
            definition.setScope("singleton");
            definition.setLazyInit(false);
            definition.setAutowireCandidate(true);
            beanDefinitionRegistry.registerBeanDefinition("Foo", definition);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
