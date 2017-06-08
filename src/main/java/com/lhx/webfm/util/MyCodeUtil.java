package com.lhx.webfm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解密操作工具
 */
public final class MyCodeUtil {
    private static final Logger log= LoggerFactory.getLogger(MyCodeUtil.class);

    /**
     * 编码url
     * @param source
     * @return
     */
    public static String encodeURL(String source){
        String target=null;
        try {
            target=URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("{}编码出错...",source);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 解码url
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target=null;
        try {
            target= URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("{}解码出错...",source);
            throw new RuntimeException(e);
        }
        return target;
    }

}
