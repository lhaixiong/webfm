package com.lhx.webfm.helper;

import com.lhx.webfm.annotation.MyAction;
import com.lhx.webfm.bean.MyHandler;
import com.lhx.webfm.bean.MyRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {
    /**
     *请求和处理器映射集合
     */
    private static final Map<MyRequest,MyHandler> ACTION_MAP=new HashMap<>();
    static {
        Set<Class<?>> ctrlClassSet = ClassHelper.getControllerClassSet();
        //循环controller注解类
        for (Class<?> ctrlClz : ctrlClassSet) {
            Method[] methods = ctrlClz.getDeclaredMethods();
            //循环方法
            for (Method method : methods) {
                //方法是否被MyAction注解
                if (method.isAnnotationPresent(MyAction.class)) {
                    MyAction action = method.getDeclaredAnnotation(MyAction.class);
                    String actionPath=action.value();
                    //验证URL映射规则
                    if (actionPath.matches("\\w+:/\\w*")) {
                        String[] splits = actionPath.split(":");
                        if (splits != null&&splits.length==2) {
                            String requestMethod=splits[0];
                            String requestPath=splits[1];
                            MyRequest req=new MyRequest(requestMethod,requestPath);
                            MyHandler handler=new MyHandler(ctrlClz,method);
                            ACTION_MAP.put(req,handler);
                        }
                    }

                }
            }
        }
    }

    /**
     * 获取handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static MyHandler getHandler(String requestMethod,String requestPath){
        MyRequest request=new MyRequest(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
