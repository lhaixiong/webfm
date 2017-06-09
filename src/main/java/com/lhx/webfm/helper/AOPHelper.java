package com.lhx.webfm.helper;

import com.lhx.webfm.annotation.MyAspect;
import com.lhx.webfm.proxy.MyAbsAspectProxy;
import com.lhx.webfm.proxy.MyProxy;
import com.lhx.webfm.proxy.MyProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * aop助手类
 */
public final class AOPHelper {
    private static final Logger log= LoggerFactory.getLogger(AOPHelper.class);
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<MyProxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<MyProxy>> entry : targetMap.entrySet()) {
                Class<?> targetClz = entry.getKey();
                List<MyProxy> proxyList = entry.getValue();
                Object proxy = MyProxyManager.createProxy(targetClz, proxyList);
                BeanHelper.addBean(targetClz,proxy);
            }
        }catch (Exception e){
            log.error("aop failure",e);
        }
    }
    /**
     * 获取切面注解标记的目标类集合
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> getTargetClassSet(MyAspect aspect) throws Exception{
        Set<Class<?>> set=new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null&&!annotation.equals(MyAspect.class)) {
            set.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return set;
    }

    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<>();
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(MyAbsAspectProxy.class);
        for (Class<?> clz : proxyClassSet) {
            if (clz.isAnnotationPresent(MyAspect.class)) {
                MyAspect aspect=clz.getAnnotation(MyAspect.class);
                Set<Class<?>> targetClassSet=getTargetClassSet(aspect);
                proxyMap.put(clz,targetClassSet);
            }
        }
        return proxyMap;
    }

    private static Map<Class<?>,List<MyProxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)
        throws Exception{
        Map<Class<?>,List<MyProxy>> targetMap=new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClz = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClz : targetClassSet) {
                MyProxy proxy=(MyProxy)proxyClz.newInstance();
                if (targetMap.containsKey(targetClz)) {
                    targetMap.get(targetClz).add(proxy);
                }else {
                    List<MyProxy> proxyList=new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClz,proxyList);
                }
            }
        }
        return targetMap;
    }

}
