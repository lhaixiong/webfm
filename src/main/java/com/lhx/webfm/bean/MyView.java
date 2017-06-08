package com.lhx.webfm.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 */
public class MyView {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String,Object> model;

    public MyView(String path){
        this.path=path;
        this.model=new HashMap<>();
    }

    /**
     * 添加模型数据
     * @param key
     * @param val
     * @return
     */
    public MyView addModel(String key,Object val){
        model.put(key,val);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
