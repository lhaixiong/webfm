package com.lhx.webfm.bean;


/**
 * 数据对象(用于写入HttpServletResponse对象中,当返回视图是json时)
 */
public class MyData {
    /**
     * 模型数据
     */
    private Object model;

    public MyData(Object model){
        this.model=model;
    }

    public Object getModel() {
        return model;
    }
}
