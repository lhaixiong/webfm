package com.lhx.webfm.helper;

import com.lhx.webfm.annotation.MyController;
import com.lhx.webfm.annotation.MyService;
import com.lhx.webfm.constant.ConfigConst;
import com.lhx.webfm.util.MyClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手
 */
public final class ClassHelper {
    /**
     * 定义类集合(用来存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;
    static {
        CLASS_SET= MyClassUtil.getClasses(ConfigConst.BASE_PACKAGE);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有MyController注解类
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> set=new HashSet<>();
        for (Class<?> clz : CLASS_SET) {
            if (clz.isAnnotationPresent(MyController.class)) {
                set.add(clz);
            }
        }
        return set;
    }

    /**
     * 获取应用包名下的所有MyService注解类
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> set=new HashSet<>();
        for (Class<?> clz : CLASS_SET) {
            if (clz.isAnnotationPresent(MyService.class)) {
                set.add(clz);
            }
        }
        return set;
    }

    /**
     * 获取应用包名下的所有自定义注解类(包括MyService、MyController注解等)
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> set=new HashSet<>();
        set.addAll(getControllerClassSet());
        set.addAll(getServiceClassSet());
        return set;
    }

}
