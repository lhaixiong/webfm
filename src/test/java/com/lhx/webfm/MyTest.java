package com.lhx.webfm;

import com.alibaba.fastjson.JSONObject;
import com.lhx.webfm.entity.User;
import com.lhx.webfm.helper.HibernateHelper;
import com.lhx.webfm.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MyTest {
    private static final Logger log= LoggerFactory.getLogger(MyTest.class);
    private Session session;
    private Transaction transaction;
    //若要添加表则一下注释去掉
//    @Before
//    public void init(){
//        log.info("初始化数据库表........");
//        session= HibernateHelper.getSession();
//        transaction=session.getTransaction();
//        transaction.begin();
//    }
//    @After
//    public void destroy(){
//        transaction.commit();
//        session.close();
//    }
    @Test
    public void test02(){
        log.error("加载类出错:{}","aaaaaa");
    }
    @Test
    public void test01(){
        UserService service=new UserService();
        String sql="select * from user";
        List<Map<String, Object>> list = service.getUsers(sql, User.class);
        for (Map<String, Object> map : list) {
            log.info(JSONObject.toJSONString(map));
        }
    }
    @Test
    public void testAddUser(){
        User user=new User();
        user.setAge(11);
        user.setName("lhx");
        session.save(user);
    }
}
