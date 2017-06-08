package com.lhx.webfm.constant;

import com.lhx.webfm.util.MyPropsUtil;

import java.util.Properties;

/**
 * 提供相关配置项常量
 */
public final class ConfigConst {
    public static String CONFIG_FILENAME="app.properties";
    public static String JDBC_DRIVER;
    public static String JDBC_URL;
    public static String JDBC_USERNAME;
    public static String JDBC_PASSWORD;
    public static String BASE_PACKAGE;
    public static String JSP_PATH;
    public static String ASSET_PATH;
    static {
        Properties props=MyPropsUtil.loadProps(CONFIG_FILENAME);
        JDBC_DRIVER= MyPropsUtil.getString(props,"jdbc.driver");
        JDBC_URL= MyPropsUtil.getString(props,"jdbc.url");
        JDBC_USERNAME= MyPropsUtil.getString(props,"jdbc.username");
        JDBC_PASSWORD= MyPropsUtil.getString(props,"jdbc.password");
        BASE_PACKAGE= MyPropsUtil.getString(props,"basePackage");
        JSP_PATH= MyPropsUtil.getString(props,"jspPath");
        ASSET_PATH= MyPropsUtil.getString(props,"assetPath");
    }
}
