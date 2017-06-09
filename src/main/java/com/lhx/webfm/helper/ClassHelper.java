package com.lhx.webfm.helper;

import com.lhx.webfm.annotation.MyController;
import com.lhx.webfm.annotation.MyDao;
import com.lhx.webfm.annotation.MyService;
import com.lhx.webfm.constant.ConfigConst;
import com.lhx.webfm.util.MyClassUtil;

import java.lang.annotation.Annotation;
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
        return getClassSetByAnnotation(MyController.class);
    }

    /**
     * 获取应用包名下的所有MyService注解类
     */
    public static Set<Class<?>> getServiceClassSet(){
        return getClassSetByAnnotation(MyService.class);
    }

    /**
     * 获取应用包名下的所有MyDao注解类
     */
    public static Set<Class<?>> getDaoClassSet(){
        return getClassSetByAnnotation(MyDao.class);
    }

    /**
     * 获取应用包名下的所有自定义注解类(包括MyController、MyService、MyDao注解等)
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> set=new HashSet<>();
        set.addAll(getControllerClassSet());
        set.addAll(getServiceClassSet());
        set.addAll(getDaoClassSet());
        return set;
    }

    /**
     * 获取应用包名下某父类(接口)的所有子类(实现)
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> set=new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (superClass.isAssignableFrom(aClass)&&!superClass.equals(aClass)) {
                set.add(aClass);
            }
        }
        return set;
    }


    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClz){
        Set<Class<?>> set=new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
           if(aClass.isAnnotationPresent(annotationClz)){
               set.add(aClass);
           }
        }
        return set;
    }
}
