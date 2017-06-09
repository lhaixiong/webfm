package com.lhx.webfm.pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxy implements InvocationHandler {
    private static MyDynamicProxy instance;
    private Object target;
    public MyDynamicProxy(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result=method.invoke(target, args);
        after();
        return result;
    }
    private void before(){
        System.out.println("before ......");
    }

    private void after(){
        System.out.println("after ......");
    }
    @SuppressWarnings("unchecked")
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        MyDynamicProxy dynamicProxy=new MyDynamicProxy(new HelloImpl());
        Hello helloProxy= (Hello)dynamicProxy.getProxy();
        helloProxy.say("dongmiao i like u");
    }
}
