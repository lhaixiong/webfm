package com.lhx.webfm.helper;

import com.lhx.webfm.annotation.MyInject;
import com.lhx.webfm.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class DIHelper {
    private static final Logger log= LoggerFactory.getLogger(DIHelper.class);
    static {
        //注解bean类与bean实例的映射集合
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (!beanMap.isEmpty()) {
            log.info("依赖注入开始....");
            //循环bean
            for (Class<?> clz : beanMap.keySet()) {
                Field[] beanFields = clz.getDeclaredFields();
                Object bean = beanMap.get(clz);
                //循环成员
                for (Field field : beanFields) {
                    //是否MyInject注解
                    if (field.isAnnotationPresent(MyInject.class)) {
                        //获取field对应的实例
                        Class<?> fieldType = field.getType();
                        Object fieldValue = beanMap.get(fieldType);
                        if (fieldValue != null) {
                            //通过反射注入到bean
                            ReflectionUtil.setField(bean,field,fieldValue);
                        }
                    }
                }
            }
            log.info("依赖注入属性结束....");
        }else {
            log.info("没有要注入属性的bean");
        }
    }
}
