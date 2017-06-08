package com.lhx.webfm.util;


/**
 * 类型转换工具类
 */
public final class MyCastUtil {
    public static String castString(Object o,String defaultValue){
        return o!=null?String.valueOf(o):defaultValue;
    }
    public static String castString(Object o){
        return castString(o,"");
    }
    public static int castInt(Object o,int defaultValue){
        int v=defaultValue;
        if (o != null) {
            v=Integer.parseInt(castString(o));
        }
        return v;
    }
    public static int castInt(Object o){
        return castInt(o,0);
    }
    public static float castFloat(Object o,float defaultValue){
        float v=defaultValue;
        if (o != null) {
            v=Float.parseFloat(castString(o));
        }
        return v;
    }
    public static float castFloat(Object o){
        return castFloat(o,0f);
    }
    public static double castDouble(Object o,double defaultValue){
        double v=defaultValue;
        if (o != null) {
            v=Double.parseDouble(castString(o));
        }
        return v;
    }
    public static double castDouble(Object o){
        return castDouble(o,0d);
    }
    public static long castLong(Object o,long defaultValue){
        long v=defaultValue;
        if (o != null) {
            v=Long.parseLong(castString(o));
        }
        return v;
    }
    public static long castLong(Object o){
        return castLong(o,0l);
    }
    public static boolean castBoolean(Object o,boolean defaultValue){
        boolean v=defaultValue;
        if (o != null) {
            v=Boolean.valueOf(castString(o));
        }
        return v;
    }
    public static boolean castBoolean(Object o){
        return castBoolean(o,false);
    }
}
