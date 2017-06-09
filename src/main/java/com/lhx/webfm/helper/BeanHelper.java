package com.lhx.webfm.helper;

import com.lhx.webfm.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean助手类(相当于bean容器)
 */
public final class BeanHelper {
    private static final Logger log= LoggerFactory.getLogger(BeanHelper.class);
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            BEAN_MAP.put(aClass, ReflectionUtil.newInstance(aClass));
        }
    }

    /**
     * 获取bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz){
        if (!BEAN_MAP.containsKey(clz)) {
            log.error("BEAN_MAP中不包含类{}",clz.getName());
            throw new RuntimeException("BEAN_MAP中不包含类"+clz);
        }
        return (T) BEAN_MAP.get(clz);
    }

    /**
     * 添加bean实例映射
     * @param clz
     * @param obj
     */
    public static void addBean(Class<?> clz,Object obj){
        BEAN_MAP.put(clz,obj);
    }
}
