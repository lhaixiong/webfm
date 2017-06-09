package com.lhx.webfm.pattern;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCgligProxy implements MethodInterceptor {
    private static MyCgligProxy instance=new MyCgligProxy();
    public static MyCgligProxy getInstance(){
        return instance;
    }
    private MyCgligProxy(){

    }
    public <T> T getProxy(Class<T> clz){
        return (T) Enhancer.create(clz,this);
    }
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, args);
        after();
        return result;
    }
    private void before(){
        System.out.println("cglib proxy before....");
    }
    private void after(){
        System.out.println("cglib proxy after....");
    }

    public static void main(String[] args) {
        Hello helloProxy = MyCgligProxy.getInstance().getProxy(HelloImpl.class);
        helloProxy.say("aaaa");
    }
}
