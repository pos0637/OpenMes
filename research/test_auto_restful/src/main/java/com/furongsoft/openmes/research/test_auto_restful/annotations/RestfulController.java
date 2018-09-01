package com.furongsoft.openmes.research.test_auto_restful.annotations;

import java.lang.annotation.*;

/**
 * 自动Restful控制器注解
 *
 * @author Alex
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestfulController {
    /**
     * 服务类型
     *
     * @return 服务类型
     */
    Class service();
}
