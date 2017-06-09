package com.lhx.webfm.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class MyProxyChain {
    private Class<?> targetClass;
    private Object targetObject;
    private Method targetMethod;
    private MethodProxy methodProxy;
    private Object[] methodParams;

    private int proxyIdx=0;
    private List<MyProxy> proxyList=new ArrayList<>();

    /**
     * 执行代理链
     * @return
     */
    public Object doProxyChain() throws Throwable {
        Object methodResout=null;
        if(proxyIdx<proxyList.size()){
            methodResout = proxyList.get(proxyIdx++).doProxy(this);
        }else {
            methodResout=methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResout;
    }

    public MyProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,
                        MethodProxy methodProxy, Object[] methodParams,List<MyProxy> list) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList=list;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public List<MyProxy> getProxyList() {
        return proxyList;
    }
}
