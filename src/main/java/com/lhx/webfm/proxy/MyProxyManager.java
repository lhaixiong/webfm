package com.lhx.webfm.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 */
public class MyProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass,List<MyProxy> list){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new MyProxyChain(targetClass,targetObject,method,methodProxy,objects,list).doProxyChain();
            }
        });
    }
}
