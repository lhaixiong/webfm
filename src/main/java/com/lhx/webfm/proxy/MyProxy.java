package com.lhx.webfm.proxy;

/**
 * 代理接口
 */
public interface MyProxy {
    public Object doProxy(MyProxyChain chain) throws Throwable;
}
