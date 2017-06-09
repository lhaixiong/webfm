package com.lhx.webfm.proxy;

import com.lhx.webfm.annotation.MyTransaction;
import com.lhx.webfm.helper.DBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class MyTransactionProxy implements MyProxy{
    private static final Logger log= LoggerFactory.getLogger(MyTransactionProxy.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER=new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(MyProxyChain chain) throws Throwable {
        Object result=null;
        boolean flag=FLAG_HOLDER.get();
        Method method=chain.getTargetMethod();
        if (!flag&&method.isAnnotationPresent(MyTransaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DBHelper.beginTransation();
                log.info("begin transation...");
                result=chain.doProxyChain();
                DBHelper.commitTransation();
                log.info("commit transation...");
            }catch (Exception e){
                DBHelper.rollbackTransation();
                log.info("rollback transation...");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result=chain.doProxyChain();
        }
        return result;
    }

}
