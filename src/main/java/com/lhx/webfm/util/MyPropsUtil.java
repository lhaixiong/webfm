package com.lhx.webfm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性工具类
 */
public final class MyPropsUtil {
    private static final Logger log= LoggerFactory.getLogger(MyPropsUtil.class);

    /**
     * 加载属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties properties=null;
        InputStream is=null;
        try {
            is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName +" file is not found");
            }
            properties=new Properties();
            properties.load(is);
        } catch (Exception e) {
           log.error("load properties file failur",e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return properties;
    }

    public static String getString(Properties props,String key){
       return getString(props,key,"");
    }
    public static String getString(Properties props,String key,String defaultValue){
        String value=defaultValue;
        if(props.containsKey(key)){
            value= props.getProperty(key);
        }
        return value;
    }

//    public static void main(String[] args) {
//        Properties props = PropsUtil.loadPros("app.properties");
//        System.out.println(props.getProperty("jdbc.username"));
//    }
}
