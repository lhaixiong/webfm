package com.lhx.webfm.helper;

import com.alibaba.fastjson.JSONObject;
import com.lhx.webfm.annotation.MyAspect;
import com.lhx.webfm.annotation.MyTransaction;
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
            log.info("AOPHelper初始化前BeanHelp中beanMap的值:");
            log.info(JSONObject.toJSONString(BeanHelper.getBeanMap()));
//            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMapNew();
            Map<Class<?>, List<MyProxy>> targetMap = createTargetMap(proxyMap);
            log.info("AOPHelper初始化proxyMap的值:");
            log.info(JSONObject.toJSONString(proxyMap));
            log.info("AOPHelper初始化targetMap的值:");
            log.info(JSONObject.toJSONString(targetMap));
            for (Map.Entry<Class<?>, List<MyProxy>> entry : targetMap.entrySet()) {
                Class<?> targetClz = entry.getKey();//目标类(controller、service、dao等clz)
                List<MyProxy> proxyList = entry.getValue();//代理对象列表
                Object proxy = MyProxyManager.createProxy(targetClz, proxyList);
                BeanHelper.addBean(targetClz,proxy);//这里不会覆盖BeanHelper中beanMap的实例么？
            }
            log.info("AOPHelper初始后前BeanHelp中beanMap的值:");
            log.info(JSONObject.toJSONString(BeanHelper.getBeanMap()));
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

    /**
     * 代理类(切面)与目标类集合(controller、service、dao等)的映射
     * ps:代理类(切面)要满足继承MyAbsAspectProxy和被MyAspect注解标记两个条件
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        /**
         * proxyMap,K:切面，v：该切面注解标记的目标类集合
         */
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

    /**
     * 代理类(切面)与目标类集合(controller、service、dao等)的映射
     * ps:代理类(切面)要满足继承MyAbsAspectProxy和被MyAspect注解标记两个条件
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMapNew() throws Exception{
        /**
         * proxyMap,K:切面，v：该切面注解标记的目标类集合
         */
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<>();
        addNormalAspectProxy(proxyMap);
        addTransationAspectProxy(proxyMap);
        return proxyMap;
    }

    private static void addNormalAspectProxy( Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(MyAbsAspectProxy.class);
        for (Class<?> clz : proxyClassSet) {
            if (clz.isAnnotationPresent(MyAspect.class)) {
                MyAspect aspect=clz.getAnnotation(MyAspect.class);
                Set<Class<?>> targetClassSet=getTargetClassSet(aspect);
                proxyMap.put(clz,targetClassSet);
            }
        }
    }

    private static void addTransationAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> serviceClassSet=ClassHelper.getServiceClassSet();
        proxyMap.put(MyTransaction.class,serviceClassSet);
    }
    /**
     * 目标类与代理对象(MyProxy)列表的映射
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,List<MyProxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)
        throws Exception{
        /**
         * targetMap K:目标类，v：代理对象列表
         */
        Map<Class<?>,List<MyProxy>> targetMap=new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClz = proxyEntry.getKey();//切面class
            Set<Class<?>> targetClassSet = proxyEntry.getValue();//切面的目标类集合(ctroller,service,dao等等)
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
