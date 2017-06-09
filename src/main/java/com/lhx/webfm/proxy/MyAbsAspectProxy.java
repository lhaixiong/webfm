package com.lhx.webfm.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理抽象父类
 */
public abstract class MyAbsAspectProxy implements MyProxy{
    protected static final Logger log= LoggerFactory.getLogger(MyAbsAspectProxy.class);
    @Override
    public final Object doProxy(MyProxyChain chain) throws Throwable{
        Object result=null;
        Class<?> targetClass = chain.getTargetClass();
        Method targetMethod = chain.getTargetMethod();
        Object[] methodParams = chain.getMethodParams();

        begin();
        try {
            if (intercept(targetClass,targetMethod,methodParams)) {
                before(targetClass,targetMethod,methodParams);
                result=chain.doProxyChain();
                after(targetClass,targetMethod,methodParams);
            }else {
                result=chain.doProxyChain();
            }

        }catch (Exception e){
            log.error("proxy failure",e);
            error(targetClass,targetMethod,methodParams,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }
    public void begin(){

    }
    public boolean intercept(Class<?> clz,Method method,Object[] args) throws Throwable{
        return true;
    }
    public void before(Class<?> clz,Method method,Object[] args) throws Throwable{
    }
    public void after(Class<?> clz,Method method,Object[] args) throws Throwable{
    }
    public void error(Class<?> clz,Method method,Object[] args,Throwable e){
    }
    public void end(){

    }
}
