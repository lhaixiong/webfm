package com.lhx.webfm.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAspect {
    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
