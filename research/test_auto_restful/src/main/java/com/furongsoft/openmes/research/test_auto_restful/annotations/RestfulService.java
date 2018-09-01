package com.furongsoft.openmes.research.test_auto_restful.annotations;

import java.lang.annotation.*;

/**
 * 自动Restful服务注解
 *
 * @author Alex
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestfulService {
    /**
     * 基础路径
     *
     * @return 基础路径
     */
    String basePath() default "";
}