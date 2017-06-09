package com.lhx.webfm.proxy;

import com.lhx.webfm.annotation.MyAspect;
import com.lhx.webfm.annotation.MyService;

import java.lang.reflect.Method;

/**
 * 代理Dao所有方法
 */
@MyAspect(MyService.class)
public class MyDaoAspect extends MyAbsAspectProxy {
    private long beginTime;
    private long endTime;

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        beginTime=System.currentTimeMillis();
        log.info("-----------------------------MyDaoAspect begin----------------------");
        log.info("class：{},method:{},begin time:{}",clz.getName(),method.getName(),beginTime);
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("-----------------------------MyDaoAspect end----------------------");
        endTime=System.currentTimeMillis();
        log.info("class：{},method:{},end time:{},time consuming:{}", clz.getName(), method.getName(),endTime,(endTime-beginTime));
    }
}
