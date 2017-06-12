package com.lhx.webfm.other;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
    private Map<Thread,T> container= Collections.synchronizedMap(new HashMap<Thread, T>());
    public void set(T t){
        Thread thread=Thread.currentThread();
        container.put(thread,t);
    }
    //这样声明泛型方法报错，原因是？
//    public <T> T get(){
//        Thread thread=Thread.currentThread();
//        T value=container.get(thread);
//        if (value == null&&!container.containsKey(thread)) {
//            value=initialValue();
//            container.put(thread,value);
//        }
//        return value;
//    }
    public T get(){
        Thread thread=Thread.currentThread();
        T value=container.get(thread);
        if (value == null&&!container.containsKey(thread)) {
            value=initialValue();
            container.put(thread,value);
        }
        return value;
    }
    protected  <T> T initialValue(){
        return null;
    }
}
