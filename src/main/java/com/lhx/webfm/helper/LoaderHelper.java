package com.lhx.webfm.helper;

import com.lhx.webfm.util.MyClassUtil;

/**
 * 加载相应的helper类
 */
public final class LoaderHelper {
    public static void init(){
        Class<?>[] classes={ClassHelper.class,
                BeanHelper.class,
                AOPHelper.class,//aop要在di之前加载
                DIHelper.class,
                ControllerHelper.class};
        for (Class<?> clz : classes) {
            MyClassUtil.loadClass(clz.getName(),true);
        }
    }
}
