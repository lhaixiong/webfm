package com.lhx.webfm.bean;

import java.lang.reflect.Method;

/**
 * 封装action方法和Controller信息
 */
public class MyHandler {
    /**
     * Controller处理类
     */
    private Class<?> ctrlClass;
    /**
     * action处理方法
     */
    private Method actionMethod;

    public MyHandler(Class<?> ctrlClass, Method actionMethod) {
        this.ctrlClass = ctrlClass;
        this.actionMethod = actionMethod;
    }
    public MyHandler(){

    }

    public Class<?> getCtrlClass() {
        return ctrlClass;
    }
    public Method getActionMethod() {
        return actionMethod;
    }

    @Override
    public String toString() {
        return "MyHandler{" +
                "ctrlClass=" + ctrlClass.getSimpleName() +
                ", actionMethod=" + actionMethod.getName() +
                '}';
    }
}
