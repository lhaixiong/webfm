package com.lhx.webfm.bean;

import java.util.Map;

/**
 * 封装请求参数
 */
public class MyParam {
    private Map<String,Object> param;

    public MyParam(Map<String, Object> param) {
        this.param = param;
    }

    public  Map<String,Object> getParamMap(){
        return param;
    }
}
